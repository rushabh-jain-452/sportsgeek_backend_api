package com.project.sportsgeek.model.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForLoginState {

    private int userId;
    private String username;
    private String role;
    private boolean status;
    private String token;
}
