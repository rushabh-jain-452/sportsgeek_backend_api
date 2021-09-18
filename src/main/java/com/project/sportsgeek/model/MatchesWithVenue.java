package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "Matches With Venue Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchesWithVenue implements Serializable {

    private int matchId;
    @NotNull
    private int tournamentId;
    @NotNull
    private String name;
    private int venueId;
    @NotNull
    private String venue;
    @NotNull
    private String team1;
    private int team1Id;
    private int team2Id;
    private String team1Short;
    @NotNull
    private String team2;
    private String team2Short;
    private String team1Logo;
    private String team2Logo;
    private int winnerTeamId;
    private int resultStatus;
    @NotNull
    private int minimumPoints;
    private Timestamp startDatetime;
}
