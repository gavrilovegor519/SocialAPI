package com.egor.socialapi.services.impl;

import com.egor.socialapi.converters.UserDTOToUserConverter;
import com.egor.socialapi.converters.UserToUserDTOConverter;
import com.egor.socialapi.dto.UserDTO;
import com.egor.socialapi.entities.Friendship;
import com.egor.socialapi.entities.User;
import com.egor.socialapi.repos.FriendshipRepo;
import com.egor.socialapi.services.FriendsService;
import com.egor.socialapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private final FriendshipRepo friendshipRepository;
    private final UserToUserDTOConverter userToUserDtoConverter;
    private final UserDTOToUserConverter userDtoToUserConverter;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Set<UserDTO>> getFriends(Long userId, String search) {
        List<Friendship> requests = friendshipRepository.findAllByUserSenderIdOrUserReceiverId(userId, userId);
        Map<Boolean, List<Friendship>> requestMap = requests.stream()
                .collect(Collectors.partitioningBy(Friendship::getAccepted));

        Predicate<User> userPredicate = u -> StringUtils.hasLength(search) ||
                (u.getUsername()).toLowerCase().contains(search.toLowerCase());
        Set<UserDTO> usersNotAcceptedRequests = requestMap.get(false).stream()
                .filter(r -> r.getUserSender().getId().equals(userId))
                .map(Friendship::getUserReceiver)
                .filter(userPredicate)
                .map(userToUserDtoConverter::convert)
                .collect(Collectors.toSet());

        Set<UserDTO> notAcceptedRequestsToUser = requestMap.get(false).stream()
                .filter(r -> r.getUserReceiver().getId().equals(userId))
                .map(Friendship::getUserSender)
                .filter(userPredicate)
                .map(userToUserDtoConverter::convert)
                .collect(Collectors.toSet());

        Set<UserDTO> friendsOfUser = requestMap.get(true).stream()
                .map(r -> r.getUserSender().getId().equals(userId) ? r.getUserReceiver() : r.getUserSender())
                .filter(userPredicate)
                .map(userToUserDtoConverter::convert)
                .collect(Collectors.toSet());

        Map<String, Set<UserDTO>> map = new HashMap<>();
        map.put("usersNotAcceptedRequests", usersNotAcceptedRequests);
        map.put("notAcceptedRequestsToUser", notAcceptedRequestsToUser);
        map.put("friendsOfUser", friendsOfUser);

        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<UserDTO> getAcceptedFriendshipUsers(Long id) {
        List<Friendship> friends = friendshipRepository.findAcceptedFriendshipUsers(id);
        return friends.stream()
                .map(r -> r.getUserSender().getId().equals(id) ? r.getUserReceiver() : r.getUserSender())
                .map(userToUserDtoConverter::convert)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void deleteFriendship(UserDTO userDTO, Long friendId) {
        User user = userDtoToUserConverter.convert(userDTO);
        User friend = userService.getUser(friendId);
        friendshipRepository.deleteFriendRequests(user, friend);
    }

    @Override
    @Transactional
    public void acceptFriendship(UserDTO userDTO, Long friendId) {
        User user = userDtoToUserConverter.convert(userDTO);
        User friend = userService.getUser(friendId);
        friendshipRepository.deleteFriendRequests(user, friend);
        friendshipRepository.addFriendship(user, friend);
    }

    @Override
    @Transactional
    public void addToFriends(UserDTO userDTO, Long friendId) {
        User user = userDtoToUserConverter.convert(userDTO);
        User friend = userService.getUser(friendId);
        friendshipRepository.addToFriends(user, friend);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkFriendship(UserDTO userDTO, UserDTO friendDTO) {
        User user = userDtoToUserConverter.convert(userDTO);
        User friend = userDtoToUserConverter.convert(friendDTO);
        return friendshipRepository.checkFriendshipExists(user, friend);
    }


}
