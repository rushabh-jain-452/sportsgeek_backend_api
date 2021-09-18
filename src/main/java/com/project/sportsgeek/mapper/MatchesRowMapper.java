package com.project.sportsgeek.mapper;


import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.Matches;
import com.project.sportsgeek.model.MatchesWithVenue;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchesRowMapper implements RowMapper<MatchesWithVenue> {
    @Override
    public MatchesWithVenue mapRow(ResultSet rs, int rowNum) throws SQLException {
        MatchesWithVenue matches = new MatchesWithVenue();
        matches.setMatchId(rs.getInt("MatchId"));
        matches.setStartDatetime(rs.getTimestamp("StartDatetime"));
        matches.setTeam1(rs.getString("team1long"));
        matches.setTeam1Short(rs.getString("team1short"));
        if(rs.getString("team1logo").isEmpty())
            matches.setTeam1Logo(rs.getString("team1logo"));
        else
            matches.setTeam1Logo(Config.FIREBASE_URL + rs.getString("team1logo") + Config.FIREBASE_PARAMS);
        matches.setTeam2(rs.getString("team2long"));
        matches.setTeam2Short(rs.getString("team2short"));
        if(rs.getString("team2logo").isEmpty())
            matches.setTeam2Logo(rs.getString("team2logo"));
        else
            matches.setTeam2Logo(Config.FIREBASE_URL + rs.getString("team2logo") + Config.FIREBASE_PARAMS);
        matches.setVenueId(rs.getInt("venueId"));
        matches.setVenue(rs.getString("venue"));
        matches.setMinimumPoints(rs.getInt("MinimumPoints"));
        matches.setWinnerTeamId(rs.getInt("WinnerTeamId"));
        matches.setResultStatus(rs.getInt("ResultStatus"));
        matches.setTournamentId(rs.getInt("TournamentId"));
        matches.setTeam1Id(rs.getInt("Team1"));
        matches.setTeam2Id(rs.getInt("Team2"));
        matches.setName(rs.getString("Name"));
        return matches;
    }
}
