package com.project.sportsgeek.model.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserResponse implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private int genderId;
    private String genderName;
    private String username;
    private String email;
    private String mobileNumber;
    private String profilePicture;
    private int roleId;
    private String roleName;
    private int availablePoints;
    private boolean status;
}
