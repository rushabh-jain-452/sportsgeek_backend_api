package com.project.sportsgeek.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContestLogFormatted {
    private String _id;
    private String text;
    private Timestamp createdAt;
    private boolean system;
}
