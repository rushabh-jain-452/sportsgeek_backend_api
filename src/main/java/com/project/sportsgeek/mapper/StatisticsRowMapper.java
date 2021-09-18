package com.project.sportsgeek.mapper;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.Statistics;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsRowMapper implements RowMapper<Statistics> {
    @Override
    public Statistics mapRow(ResultSet rs, int rowNum) throws SQLException {
        Statistics statistics = new Statistics();
        statistics.setLastName(rs.getString("LastName"));
        statistics.setFirstName(rs.getString("FirstName"));
        statistics.setUserName(rs.getString("UserName"));
        statistics.setUserId(rs.getInt("UserId"));
        if(rs.getString("ProfilePicture").isEmpty())
            statistics.setProfilePicture(rs.getString("ProfilePicture"));
        else
            statistics.setProfilePicture(Config.FIREBASE_URL + rs.getString("ProfilePicture") + Config.FIREBASE_PARAMS);
        statistics.setAvailablePoints(rs.getInt("AvailablePoints"));
        return statistics;
    }
}
