package com.project.sportsgeek.mapper;


import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamRowMapper implements RowMapper<Team> {
    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        Team team =new Team();
        team.setTeamId(rs.getInt("TeamId"));
        team.setName(rs.getString("Name"));
        team.setShortName(rs.getString("ShortName"));
//        team.setTeamLogo(Config.FIREBASE_URL + rs.getString("TeamLogo") + "?alt=media&token=" + Config.FIREBASE_TOKEN);
        if(rs.getString("TeamLogo").isEmpty())
            team.setTeamLogo(rs.getString("TeamLogo"));
        else
            team.setTeamLogo(Config.FIREBASE_URL + rs.getString("TeamLogo") + Config.FIREBASE_PARAMS);
        return team;
    }
}
