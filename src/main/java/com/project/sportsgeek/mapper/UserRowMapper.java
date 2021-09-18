package com.project.sportsgeek.mapper;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.profile.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("UserId"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setGenderId(rs.getInt("GenderId"));
        user.setUsername(rs.getString("UserName"));
        if(rs.getString("ProfilePicture").isEmpty())
            user.setProfilePicture(rs.getString("ProfilePicture"));
        else
            user.setProfilePicture(Config.FIREBASE_URL + rs.getString("ProfilePicture") + Config.FIREBASE_PARAMS);
        user.setRoleId(rs.getInt("RoleId"));
        user.setAvailablePoints(rs.getInt("AvailablePoints"));
        user.setStatus(rs.getBoolean("Status"));
        return user;
    }

}
