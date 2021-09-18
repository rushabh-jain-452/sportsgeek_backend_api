package com.project.sportsgeek.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContestWithUser {
    private int contestId;
    private int userId;
    private int matchId;
    private int teamId;
    private String shortName;
    private int contestPoints;
    private int winningPoints;
    private String firstName;
    private String lastName;
    private String username;
    private String profilePicture;
}
