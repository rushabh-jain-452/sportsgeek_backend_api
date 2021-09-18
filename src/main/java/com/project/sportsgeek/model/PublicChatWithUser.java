package com.project.sportsgeek.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicChatWithUser {
    private int publicChatId;
    private int userId;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String message;
    private boolean status;
    private Timestamp chatTimestamp;
}
