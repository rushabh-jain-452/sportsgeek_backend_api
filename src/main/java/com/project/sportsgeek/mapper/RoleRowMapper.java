package com.project.sportsgeek.mapper;

import com.project.sportsgeek.model.profile.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {

        Role role = new Role();
        role.setRoleId(rs.getInt("RoleId"));
        role.setName(rs.getString("Name"));
        return role;
    }


}
