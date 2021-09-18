package com.project.sportsgeek.model.profile;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserWithNewPassword {

    @Min(1)
    @JsonAlias({"UserId"})
    private int userId;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
