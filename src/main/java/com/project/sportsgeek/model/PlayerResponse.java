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
@ApiModel(description = "Player Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerResponse extends Player implements Serializable {

    private int playerId;
    private String team;
    private String name;
    private String playerType;
    private String profilePicture;
}
