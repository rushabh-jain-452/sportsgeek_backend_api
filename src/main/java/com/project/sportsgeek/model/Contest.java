package com.project.sportsgeek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
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
public class Contest implements Serializable {

    private int contestId;
    @NotNull
    private int userId;
    @NotNull
    private int matchId;
    @NotNull
    private int teamId;
    private int contestPoints;
    private int winningPoints;
}
