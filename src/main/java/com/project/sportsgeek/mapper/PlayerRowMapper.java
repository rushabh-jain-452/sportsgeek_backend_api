package com.project.sportsgeek.mapper;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.Player;
import com.project.sportsgeek.model.PlayerResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerRowMapper implements RowMapper<PlayerResponse> {
    @Override
    public PlayerResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        PlayerResponse player = new PlayerResponse();
        player.setPlayerId(rs.getInt("PlayerId"));
        player.setName(rs.getString("Name"));
        player.setPlayerType(rs.getString("PlayerType"));
        if(rs.getString("ProfilePicture").isEmpty())
            player.setProfilePicture(rs.getString("ProfilePicture"));
        else
            player.setProfilePicture(Config.FIREBASE_URL + rs.getString("ProfilePicture") + Config.FIREBASE_PARAMS);
        player.setTeam(rs.getString("TeamName"));
        player.setTypeId(rs.getInt("PlayerTypeId"));
        player.setTeamId(rs.getInt("TeamId"));
        return player;
    }
}
