package com.project.sportsgeek.repository.playertyperepo;

import com.project.sportsgeek.mapper.PlayerTypeRowMapper;
import com.project.sportsgeek.model.PlayerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "playerTypeRepo")
public class PlayerTypeRepoImpl implements PlayerTypeRepository{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<PlayerType> findAllPlayerType() {
        String sql = "SELECT * FROM PlayerType";
        return jdbcTemplate.query(sql,new PlayerTypeRowMapper());
    }

    @Override
    public PlayerType findPlayerTypeById(int playerTypeId) throws Exception {
        String sql = "SELECT * FROM PlayerType WHERE PlayerTypeId=:playerTypeId";
        MapSqlParameterSource params = new MapSqlParameterSource("playerTypeId", playerTypeId);
        List<PlayerType> playerTypeList = jdbcTemplate.query(sql, params, new PlayerTypeRowMapper());
        if(playerTypeList.size() > 0){
            return playerTypeList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int addPlayerType(PlayerType playerType) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO PlayerType (TypeName) VALUES(:typeName)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(playerType), holder);
        if(n > 0 && holder.getKeys().size() > 0) {
//            return holder.getKey().intValue();
            return (int)holder.getKeys().get("PlayerTypeId");
        }
        return 0;
    }

    @Override
    public boolean updatePlayerType(int playerTypeId, PlayerType playerType) throws Exception {
        String sql = "UPDATE PlayerType set TypeName = :typeName where PlayerTypeId=:playerTypeId";
        playerType.setPlayerTypeId(playerTypeId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(playerType)) > 0;
    }

    @Override
    public boolean deletePlayerType(int playerTypeId) throws Exception {
        String sql = "DELETE FROM PlayerType WHERE PlayerTypeId =:playerTypeId";
        MapSqlParameterSource params = new MapSqlParameterSource("playerTypeId", playerTypeId);
        return  jdbcTemplate.update(sql, params) > 0;
    }
}
