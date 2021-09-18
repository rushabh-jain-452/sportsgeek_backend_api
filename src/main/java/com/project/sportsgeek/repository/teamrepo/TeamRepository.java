package com.project.sportsgeek.repository.teamrepo;

import com.project.sportsgeek.model.Team;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "teamRepo")
public interface TeamRepository {

    List<Team> findAllTeam();

    Team findTeamById(int teamId) throws Exception;

    int addTeam(Team team) throws Exception;

    boolean updateTeam(int teamId, Team team) throws Exception;

    boolean updateTeamWithLogo(int teamId, Team team) throws Exception;

    boolean deleteTeam(int teamId) throws Exception;

}
