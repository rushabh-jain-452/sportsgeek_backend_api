package com.project.sportsgeek.mapper;

import com.project.sportsgeek.model.Gender;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderRowMapper implements RowMapper<Gender> {

    @Override
    public Gender mapRow(ResultSet rs, int rowNum) throws SQLException {

        Gender gender = new Gender();
        gender.setGenderId(rs.getInt("GenderId"));
        gender.setName(rs.getString("Name"));
        return gender;
    }

}
