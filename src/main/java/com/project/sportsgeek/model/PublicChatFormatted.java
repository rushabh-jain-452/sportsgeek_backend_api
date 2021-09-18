package com.project.sportsgeek.model;

import com.project.sportsgeek.model.profile.UserForChat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicChatFormatted {
    private int _id;
    private String text;
    private boolean status;
    private Timestamp createdAt;
    private UserForChat user;
}
