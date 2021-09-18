package com.project.sportsgeek.model.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private int userId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private int genderId;
    @NotNull
    private String username;
    private String email;
    private String mobileNumber;
    private String profilePicture;
    @NotNull
    private int roleId;
    private int availablePoints;
    private boolean status;

//    public User(UserWithPassword userWithPassword) {
//        super();
//        UserId = userWithPassword.getUserId();
//        FirstName = userWithPassword.getFirstName();
//        LastName = userWithPassword.getLastName();
//        GenderId = userWithPassword.getGenderId();
//        Username = userWithPassword.getUsername();
//        ProfilePicture = userWithPassword.getProfilePicture();
//        RoleId = userWithPassword.getRoleId();
//        AvailablePoints = userWithPassword.getAvailablePoints();
//        status = userWithPassword.isStatus();
//    }

}
