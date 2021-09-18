package com.project.sportsgeek.model.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserWithPassword extends User {

    @NotNull
    String password;
    String role;
}
