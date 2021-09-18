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
@ApiModel(description = "PlayerType Model")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerType implements Serializable {

    private int playerTypeId;
    @NotNull
    private String typeName;
}
