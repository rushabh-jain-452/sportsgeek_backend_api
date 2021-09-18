package com.project.sportsgeek.mapper;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.ContestWithResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContestWithUsersResultRowMapper implements RowMapper<ContestWithResult> {
    @Override
    public ContestWithResult mapRow(ResultSet rs, int rowNum) throws SQLException {

        ContestWithResult contestWithResult = new ContestWithResult();
        contestWithResult.setContestId(rs.getInt("ContestId"));
        contestWithResult.setContestPoints(rs.getInt("ContestPoints"));
        contestWithResult.setFirstName(rs.getString("FirstName"));
        contestWithResult.setLastName(rs.getString("LastName"));
        contestWithResult.setTeamShortName(rs.getString("TeamShortName"));
        contestWithResult.setWinningPoints(rs.getInt("WinningPoints"));
        if(rs.getString("ProfilePicture").isEmpty())
            contestWithResult.setProfilePicture(rs.getString("ProfilePicture"));
        else
            contestWithResult.setProfilePicture(Config.FIREBASE_URL + rs.getString("ProfilePicture") + Config.FIREBASE_PARAMS);
        contestWithResult.setUsername(rs.getString("Username"));
        return  contestWithResult;
    }
}
