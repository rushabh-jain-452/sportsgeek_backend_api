package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "Contest Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContestWithResult implements Serializable {
    private int contestId;
    private String teamShortName;
    private String firstName;
    private String lastName;
    private String username;
    private String profilePicture;
    private int contestPoints;
    private int winningPoints;

//    public BetOnTeamWithResult() {
//    }
//
//    public BetOnTeamWithResult(int betTeamId, String teamShortName, String firstName, String lastName, String username, String profilePicture, int betPoints, int winningPoints) {
//        betTeamId = betTeamId;
//        teamShortName = teamShortName;
//        firstName = firstName;
//        lastName = lastName;
//        username = username;
//        profilePicture = profilePicture;
//        betPoints = betPoints;
//        winningPoints = winningPoints;
//    }
//
//    public int getBetTeamId() {
//        return betTeamId;
//    }
//
//    public void setBetTeamId(int betTeamId) {
//        betTeamId = betTeamId;
//    }
//
//    public String getTeamShortName() {
//        return teamShortName;
//    }
//
//    public void setTeamShortName(String teamShortName) {
//        teamShortName = teamShortName;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        lastName = lastName;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        username = username;
//    }
//
//    public String getProfilePicture() {
//        return profilePicture;
//    }
//
//    public void setProfilePicture(String profilePicture) {
//        profilePicture = profilePicture;
//    }
//
//    public int getBetPoints() {
//        return BetPoints;
//    }
//
//    public void setBetPoints(int betPoints) {
//        BetPoints = betPoints;
//    }
//
//    public int getWinningPoints() {
//        return WinningPoints;
//    }
//
//    public void setWinningPoints(int winningPoints) {
//        WinningPoints = winningPoints;
//    }
}
