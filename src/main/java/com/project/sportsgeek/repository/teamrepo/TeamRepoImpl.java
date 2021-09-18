package com.project.sportsgeek.repository.teamrepo;

import com.project.sportsgeek.mapper.TeamRowMapper;
import com.project.sportsgeek.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "teamRepo")
public class TeamRepoImpl implements TeamRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Team> findAllTeam() {
        String sql = "SELECT * from Team";
        return jdbcTemplate.query(sql, new TeamRowMapper());
    }

    @Override
    public Team findTeamById(int teamId) throws Exception{
        String sql = "SELECT * FROM Team WHERE TeamId =:teamId";
        Team team = new Team();
        team.setTeamId(teamId);
        List<Team> teamList = jdbcTemplate.query(sql,new BeanPropertySqlParameterSource(team),new TeamRowMapper());
        if(teamList.size() > 0){
            return teamList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int addTeam(Team team) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Team (Name,ShortName,TeamLogo) VALUES(:name,:shortName,:teamLogo)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(team), holder);
        if(n > 0 && holder.getKeys().size() > 0) {
//            return holder.getKey().intValue();
            return (int)holder.getKeys().get("TeamId");
        }else {
            return 0;
        }
    }

    @Override
    public boolean updateTeam(int teamId, Team team) throws Exception {
        String sql = "UPDATE Team SET Name = :name, ShortName = :shortName where TeamId=:teamId";
        team.setTeamId(teamId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(team)) > 0;
    }

    @Override
    public boolean updateTeamWithLogo(int teamId, Team team) throws Exception {
        String sql = "UPDATE Team SET Name = :name, ShortName = :shortName, TeamLogo = :teamLogo where TeamId=:teamId";
        team.setTeamId(teamId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(team)) > 0;
    }

    @Override
    public boolean deleteTeam(int teamId) throws Exception {
        String sql = "DELETE FROM Team WHERE TeamId =:teamId";
        Team team = new Team();
        team.setTeamId(teamId);
        return jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(team)) > 0;
    }
}
