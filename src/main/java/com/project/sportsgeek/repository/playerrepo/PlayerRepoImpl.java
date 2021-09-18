package com.project.sportsgeek.repository.playerrepo;

import com.project.sportsgeek.mapper.PlayerRowMapper;
import com.project.sportsgeek.model.Player;
import com.project.sportsgeek.model.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "playerRepo")
public class PlayerRepoImpl implements PlayerRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<PlayerResponse> findAllPlayers() {
//       String sql = "SELECT PlayerId,TeamId,Name,TypeId,ProfilePicture FROM Player";
        String sql = "SELECT p.PlayerId as PlayerId, t.Name as TeamName, p.Name as Name,pt.TypeName as PlayerType, p.ProfilePicture as ProfilePicture, t.TeamId as TeamId,pt.PlayerTypeId as PlayerTypeId " +
                "FROM Player as p INNER JOIN PlayerType as pt on p.TypeId = pt.PlayerTypeId INNER JOIN Team as t on p.TeamId=t.TeamId";
       return jdbcTemplate.query(sql, new PlayerRowMapper());
    }

    @Override
    public PlayerResponse findPlayerByPlayerId(int playerId) throws Exception {
//        String sql = "SELECT PlayerId,TeamId,Name,TypeId,ProfilePicture FROM Player WHERE PlayerId="+id;
        String sql = "SELECT p.PlayerId as PlayerId, t.Name as TeamName, p.Name as Name,pt.TypeName as PlayerType, p.ProfilePicture as ProfilePicture, t.TeamId as TeamId,pt.PlayerTypeId as PlayerTypeId " +
                "FROM Player as p INNER JOIN PlayerType as pt on p.TypeId = pt.PlayerTypeId INNER JOIN Team as t on p.TeamId=t.TeamId WHERE p.PlayerId=:playerId";
        MapSqlParameterSource params = new MapSqlParameterSource("playerId", playerId);
        List<PlayerResponse> playerList = jdbcTemplate.query(sql, params, new PlayerRowMapper());
        if(playerList.size() > 0){
            return playerList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<PlayerResponse> findPlayerByPlayerType(int playerTypeId) throws Exception {
//        String sql = "SELECT PlayerId,TeamId,Name,TypeId,ProfilePicture FROM Player WHERE TypeId="+id;
        String sql = "SELECT p.PlayerId as PlayerId, t.Name as TeamName, p.Name as Name,pt.TypeName as PlayerType, p.ProfilePicture as ProfilePicture, t.TeamId as TeamId,pt.PlayerTypeId as PlayerTypeId " +
                "FROM Player as p INNER JOIN PlayerType as pt on p.TypeId = pt.PlayerTypeId INNER JOIN Team as t on p.TeamId=t.TeamId WHERE p.TypeId=:playerTypeId";
        MapSqlParameterSource params = new MapSqlParameterSource("playerTypeId", playerTypeId);
        return jdbcTemplate.query(sql, params, new PlayerRowMapper());
    }

    @Override
    public List<PlayerResponse> findPlayerByTeamId(int teamId) throws Exception {
//        String sql = "SELECT PlayerId,TeamId,Name,TypeId,ProfilePicture FROM Player WHERE TeamId="+id;
        String sql = "SELECT p.PlayerId as PlayerId, t.Name as TeamName, p.Name as Name,pt.TypeName as PlayerType, p.ProfilePicture as ProfilePicture, t.TeamId as TeamId,pt.PlayerTypeId as PlayerTypeId " +
                "FROM Player as p INNER JOIN PlayerType as pt on p.TypeId = pt.PlayerTypeId INNER JOIN Team as t on p.TeamId=t.TeamId WHERE p.TeamId=:teamId";
        MapSqlParameterSource params = new MapSqlParameterSource("teamId", teamId);
        return jdbcTemplate.query(sql, params, new PlayerRowMapper());
    }

    @Override
    public int addPlayer(Player player) throws Exception {
        String sql = "INSERT INTO Player (PlayerId,TeamId,Name,TypeId,ProfilePicture) VALUES(:playerId,:teamId,:name,:typeId,:profilePicture)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(player));
        if(n > 0) {
            return player.getPlayerId();
        }
        return 0;
    }

    @Override
    public boolean updatePlayer(int playerId, Player player) throws Exception {
        String sql = "UPDATE Player SET TeamId = :teamId, Name = :name, TypeId = :typeId where PlayerId=:playerId";
        player.setPlayerId(playerId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(player)) > 0;
    }

    @Override
    public boolean updatePlayerWithProfilePicture(int playerId, Player player) throws Exception {
        String sql = "UPDATE Player SET TeamId = :teamId, Name = :name, TypeId = :typeId, ProfilePicture = :profilePicture where PlayerId=:playerId";
        player.setPlayerId(playerId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(player)) > 0;
    }

    @Override
    public boolean updatePlayerType(int playerId, int playerTypeId) throws Exception {
        String sql = "UPDATE Player SET TypeId=:typeId WHERE PlayerId=:playerId";
        Player player = new Player();
        MapSqlParameterSource params = new MapSqlParameterSource("playerId", playerId);
        params.addValue("typeId", playerTypeId);
        return jdbcTemplate.update(sql, params)> 0;
    }

    @Override
    public boolean deletePlayer(int playerId) throws Exception {
        String sql = "DELETE FROM Player WHERE PlayerId=:playerId";
        MapSqlParameterSource params = new MapSqlParameterSource("playerId", playerId);
        return  jdbcTemplate.update(sql, params) > 0;
    }
}
