package com.project.sportsgeek.mapper;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.ContestWithUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContestRowMapper implements RowMapper<ContestWithUser> {

    @Override
    public ContestWithUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        ContestWithUser contest = new ContestWithUser();
        contest.setContestId(rs.getInt("ContestId"));
        contest.setMatchId(rs.getInt("MatchId"));
        contest.setUserId(rs.getInt(rs.getInt("UserId")));
        contest.setContestPoints(rs.getInt("ContestPoints"));
        contest.setUsername(rs.getString("Username"));
        contest.setTeamId(rs.getInt("TeamId"));
        contest.setShortName(rs.getString("TeamShortName"));
        if(rs.getString("ProfilePicture").isEmpty())
            contest.setProfilePicture(rs.getString("ProfilePicture"));
        else
            contest.setProfilePicture(Config.FIREBASE_URL + rs.getString("ProfilePicture") + Config.FIREBASE_PARAMS);
        contest.setFirstName(rs.getString("FirstName"));
        contest.setLastName(rs.getString("LastName"));
        contest.setWinningPoints(rs.getInt("WinningPoints"));
        return contest;
    }
}
