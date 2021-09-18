package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

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
public class ContestLog {
    private int contestLogId;
    @NotNull
    private int userId;
    @NotNull
    private int matchId;
    @NotNull
    private Integer oldTeamId;
    private Integer oldContestPoints;
    @NotNull
    private int newTeamId;
    private int newContestPoints;
    private String action;
    private Timestamp logTimestamp;
}
