package com.project.sportsgeek.mapper;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.MyMatches;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyMatchesResultRowMapper implements RowMapper<MyMatches> {
    @Override
    public MyMatches mapRow(ResultSet rs, int rowNum) throws SQLException {
        MyMatches myMatchesResult = new MyMatches();
        if(rs.getString("Team1Logo").isEmpty())
            myMatchesResult.setTeam1Logo(rs.getString("Team1Logo"));
        else
            myMatchesResult.setTeam1Logo(Config.FIREBASE_URL + rs.getString("Team1Logo") + Config.FIREBASE_PARAMS);
        myMatchesResult.setContestPoints(rs.getInt("ContestPoints"));
        myMatchesResult.setTeam1Short(rs.getString("Team1Short"));
        if(rs.getString("Team2Logo").isEmpty())
            myMatchesResult.setTeam2Logo(rs.getString("Team2Logo"));
        else
            myMatchesResult.setTeam2Logo(Config.FIREBASE_URL + rs.getString("Team2Logo") + Config.FIREBASE_PARAMS);
        myMatchesResult.setVenue(rs.getString("Venue"));
        myMatchesResult.setTeam2Short(rs.getString("Team2Short"));
        myMatchesResult.setTeamName(rs.getString("TeamName"));
        myMatchesResult.setStartDatetime(rs.getTimestamp("StartDateTime"));
        myMatchesResult.setWinningPoints(rs.getInt("WinningPoints"));
        myMatchesResult.setWinnerTeamName(rs.getString("WinnerTeam"));
        myMatchesResult.setResultStatus(rs.getInt("ResultStatus"));
        myMatchesResult.setMatchId(rs.getInt("MatchId"));
        return myMatchesResult;
    }
}
