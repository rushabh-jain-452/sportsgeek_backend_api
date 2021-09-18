package com.project.sportsgeek.repository.rolerepo;

import com.project.sportsgeek.model.profile.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "roleRepo")
public interface RoleRepository {

    List<Role> findAllRole();

    Role findRoleById(int roleId) throws Exception;

    int addRole(Role role) throws Exception;

    boolean updateRole(int roleId, Role role) throws Exception;

    boolean deleteRole(int roleId) throws Exception;

}
