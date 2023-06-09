package com.egor.socialapi.repos;

import com.egor.socialapi.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Long> {

    Role getRoleByName(String name);
}
