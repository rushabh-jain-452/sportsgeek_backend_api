package com.project.sportsgeek.repository.tournamentrepo;

import com.project.sportsgeek.mapper.TournamentRowMapper;
import com.project.sportsgeek.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "tournamentRepo")
public class TournamentRepoImpl implements TournamentRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Tournament> findAllTournament() {
        String sql = "SELECT * from Tournament";
        return jdbcTemplate.query(sql, new TournamentRowMapper());
    }

    @Override
    public Tournament findTournamentById(int tournamentId) throws Exception {
        String sql = "SELECT * FROM Tournament WHERE TournamentId = :tournamentId";
        MapSqlParameterSource params = new MapSqlParameterSource("tournamentId", tournamentId);
        List<Tournament> tournamentList = jdbcTemplate.query(sql, params, new TournamentRowMapper());
        if(tournamentList.size() > 0){
            return tournamentList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Tournament findTournamentByActive() throws Exception {
        String sql = "SELECT * from Tournament WHERE active = true";
        List<Tournament> tournamentList = jdbcTemplate.query(sql, new TournamentRowMapper());
        if(tournamentList.size() > 0){
            return tournamentList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int addTournament(Tournament tournament) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Tournament (Name, active) VALUES(:name, 0)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(tournament), holder);
        if(n > 0 && holder.getKeys().size() > 0) {
//            return holder.getKey().intValue();
            return (int)holder.getKeys().get("TournamentId");
        }else {
            return 0;
        }

    }

    @Override
    public boolean updateTournament(int tournamentId, Tournament tournament) throws Exception {
        String sql = "UPDATE Tournament SET Name = :name where TournamentId = :tournamentId";
        tournament.setTournamentId(tournamentId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(tournament)) > 0;
    }

    @Override
    public boolean updateActiveTournament(int tournamentId) throws Exception {
        String sql = "UPDATE Tournament SET active=1 WHERE TournamentId = :tournamentId";
        MapSqlParameterSource params = new MapSqlParameterSource("tournamentId", tournamentId);
        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean deactivateTournament() throws Exception {
        String sql = "UPDATE Tournament SET active=0 WHERE active=1";
//        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(null)) > 0;
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(sql)) > 0;
    }

    @Override
    public boolean deleteTournament(int tournamentId) throws Exception {
        String sql = "DELETE FROM Tournament WHERE TournamentId = :tournamentId";
        MapSqlParameterSource params = new MapSqlParameterSource("tournamentId", tournamentId);
        return jdbcTemplate.update(sql, params) > 0;
    }
}
