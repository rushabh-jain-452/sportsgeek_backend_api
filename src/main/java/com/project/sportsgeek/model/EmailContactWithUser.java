package com.project.sportsgeek.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailContactWithUser extends EmailContact{

    private String Username;
    private String firstName;
    private String lastName;

}
