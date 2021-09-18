package com.project.sportsgeek.mapper;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.profile.User;
import com.project.sportsgeek.model.profile.UserResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResponseRowMapper implements RowMapper<UserResponse> {
    @Override
    public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserResponse user = new UserResponse();
        try{
            user.setUserId(rs.getInt("UserId"));
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setGenderId(rs.getInt("GenderId"));
            user.setGenderName(rs.getString("GenderName"));
            user.setUsername(rs.getString("UserName"));
            user.setEmail(rs.getString("Email"));
            user.setMobileNumber(rs.getString("MobileNumber"));
            System.out.println(rs.getString("ProfilePicture"));
            if(rs.getString("ProfilePicture").isEmpty())
                user.setProfilePicture(rs.getString("ProfilePicture"));
            else
                user.setProfilePicture(Config.FIREBASE_URL + rs.getString("ProfilePicture") + Config.FIREBASE_PARAMS);
            user.setRoleId(rs.getInt("RoleId"));
            user.setRoleName(rs.getString("RoleName"));
            user.setAvailablePoints(rs.getInt("AvailablePoints"));
            user.setStatus(rs.getBoolean("Status"));
            return user;
        }catch(Exception ex){
//            ex.printStackTrace();
            return user;
        }
    }
}
