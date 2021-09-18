package com.project.sportsgeek.service;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.profile.Role;
import com.project.sportsgeek.repository.rolerepo.RoleRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    @Qualifier("roleRepo")
    RoleRepository roleRepository;

    public Result<List<Role>> findAllRole() {
        List<Role> roleList = roleRepository.findAllRole();
        return new Result<>(200, roleList);
    }

    public Result<Role> findRoleById(int roleId) throws Exception {
        Role role = roleRepository.findRoleById(roleId);
        if (role != null) {
            return new Result<>(200, role);
        }
        throw new ResultException(new Result<>(404, "Role with RoleId: " + roleId + " not found."));
    }

    public Result<Role> addRole(Role role) throws Exception {
        int id = roleRepository.addRole(role);
        if (id > 0) {
            role.setRoleId(id);
            return new Result<>(201, role);
        }
        throw new ResultException(new Result<>(400, "Unable to add Gender."));
    }

    public Result<Role> updateRole(int roleId, Role role) throws Exception {
        if (roleRepository.updateRole(roleId, role)) {
            return new Result<>(200, role);
        }
        throw new ResultException(new Result<>(404, "Role with RoleId: " + roleId + " not found."));
    }

    public Result<String> deleteRole(int roleId) throws Exception {
        if (roleRepository.deleteRole(roleId)) {
            return new Result<>(200, "Role deleted successfully.");
        }
        throw new ResultException(new Result<>(404, "Role with RoleId: " + roleId + " not found."));
    }
}
