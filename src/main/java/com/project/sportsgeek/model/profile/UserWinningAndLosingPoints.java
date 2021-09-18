package com.project.sportsgeek.model.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWinningAndLosingPoints implements Serializable {

    private int userId;
    private int winningPoints;
    private int losingPoints;
    private int numberOfWinningMatches;
    private int numberOfLosingMatches;
}
