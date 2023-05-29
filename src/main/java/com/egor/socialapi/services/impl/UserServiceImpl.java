package com.egor.socialapi.services.impl;

import com.egor.socialapi.converters.PageToPageDTOUserConverter;
import com.egor.socialapi.converters.UserDTOToUserConverter;
import com.egor.socialapi.converters.UserToUserDTOConverter;
import com.egor.socialapi.dto.PageDTO;
import com.egor.socialapi.dto.UserDTO;
import com.egor.socialapi.entities.Role;
import com.egor.socialapi.entities.User;
import com.egor.socialapi.exception.SocialNetworkException;
import com.egor.socialapi.exception.UserNotFoundException;
import com.egor.socialapi.repos.RoleRepo;
import com.egor.socialapi.repos.UserRepo;
import com.egor.socialapi.security.JwtUtilities;
import com.egor.socialapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.egor.socialapi.constants.Constants.ROLE_ADMIN;
import static com.egor.socialapi.constants.Constants.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtilities ;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserToUserDTOConverter userToUserDtoConverter;
    private final UserDTOToUserConverter userDtoToUserConverter;
    private final PageToPageDTOUserConverter pageToPageDtoUserConverter;

    private static void checkSuperAdmin(User user) {
        if (user.getId() == 1)
            throw new SocialNetworkException("It is not allowed to change roles for the user with id = 1");
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User with id = %s is not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = getUser(id);
        return userToUserDtoConverter.convert(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return userToUserDtoConverter.convert(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDTO<UserDTO> findAllPageable(Long id, Pageable pageable) {
        Page<User> pagedUsers = userRepository.findAllByIdNot(id, pageable);
        return pageToPageDtoUserConverter.convert(pagedUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDTO<UserDTO> findAllWithSearch(Long id, String search, Pageable pageable) {
        search = String.format("%%%s%%", search).toLowerCase();
        Page<User> pagedUsers = userRepository.findAllWithSearch(id, search, pageable);
        return pageToPageDtoUserConverter.convert(pagedUsers);
    }

    @Override
    @Transactional
    public void createUser(UserDTO userDTO) {
        User user = userDtoToUserConverter.convert(userDTO);
        assert user != null;
        user.setRoles(new ArrayList<>());

        Role role = roleRepository.getRoleByName(ROLE_USER.toString());
        user.getRoles().add(role);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(String password, Long id) {
        String encodedPassword = passwordEncoder.encode(password);
        userRepository.updatePassword(encodedPassword, id);
    }

    @Override
    @Transactional
    public void makeUserAdmin(Long userId) {
        User user = getUser(userId);
        checkSuperAdmin(user);
        Role role = roleRepository.getRoleByName(ROLE_ADMIN.toString());
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void blockUser(Long userId) {
        User user = getUser(userId);
        checkSuperAdmin(user);
        user.setRoles(new ArrayList<>());
        userRepository.save(user);
    }

    @Override
    public String authenticate(UserDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findUserByEmail(authentication.getName());
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r-> rolesNames.add(r.getAuthority()));
        return jwtUtilities.generateToken(user.getUsername(), rolesNames);
    }
}
