package com.project.sportsgeek.mapper;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.PublicChatFormatted;
import com.project.sportsgeek.model.profile.UserForChat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicChatFormattedRowMapper implements RowMapper<PublicChatFormatted> {
    @Override
    public PublicChatFormatted mapRow(ResultSet rs, int rowNum) throws SQLException {
        PublicChatFormatted publicChatFormatted = new PublicChatFormatted();
        publicChatFormatted.set_id(rs.getInt("PublicChatId"));
        publicChatFormatted.setText(rs.getString("Message"));
        publicChatFormatted.setStatus(rs.getBoolean("Status"));
        publicChatFormatted.setCreatedAt(rs.getTimestamp("ChatTimestamp"));
        // UserForChat
        UserForChat userForChat = new UserForChat();
        userForChat.set_id(rs.getInt("UserId"));
        userForChat.setName(rs.getString("FirstName") + " " + rs.getString("LastName"));
        if(rs.getString("ProfilePicture").isEmpty())
            userForChat.setAvatar(rs.getString("ProfilePicture"));
        else
            userForChat.setAvatar(Config.FIREBASE_URL + rs.getString("ProfilePicture") + Config.FIREBASE_PARAMS);
        publicChatFormatted.setUser(userForChat);
        return publicChatFormatted;
    }
}
