package com.project.sportsgeek.mapper;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.MyMatches;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyMatchesRowMapper implements RowMapper<MyMatches> {
    @Override
    public MyMatches mapRow(ResultSet rs, int rowNum) throws SQLException {
        MyMatches myMatches = new MyMatches();
        if(rs.getString("Team1Logo").isEmpty())
            myMatches.setTeam1Logo(rs.getString("Team1Logo"));
        else
            myMatches.setTeam1Logo(Config.FIREBASE_URL + rs.getString("Team1Logo") + Config.FIREBASE_PARAMS);
        myMatches.setContestPoints(rs.getInt("ContestPoints"));
        myMatches.setTeam1Short(rs.getString("Team1Short"));
        if(rs.getString("Team2Logo").isEmpty())
            myMatches.setTeam2Logo(rs.getString("Team2Logo"));
        else
            myMatches.setTeam2Logo(Config.FIREBASE_URL + rs.getString("Team2Logo") + Config.FIREBASE_PARAMS);
        myMatches.setVenue(rs.getString("Venue"));
        myMatches.setTeam2Short(rs.getString("Team2Short"));
        myMatches.setTeamName(rs.getString("TeamName"));
        myMatches.setStartDatetime(rs.getTimestamp("StartDateTime"));
        myMatches.setMatchId(rs.getInt("MatchId"));
        return myMatches;
    }
}
