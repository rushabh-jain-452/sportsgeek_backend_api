package com.project.sportsgeek.repository.tournamentrepo;

import com.project.sportsgeek.model.Tournament;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "tournamentRepo")
public interface TournamentRepository {

    List<Tournament> findAllTournament();

    Tournament findTournamentById(int tournamentId) throws  Exception;

    Tournament findTournamentByActive() throws Exception;

    int addTournament(Tournament tournament) throws Exception;

    boolean updateTournament(int tournamentId, Tournament tournament) throws Exception;

    boolean updateActiveTournament(int tournamentId) throws Exception;

    boolean deactivateTournament() throws Exception;

    boolean deleteTournament(int tournamentId) throws Exception;

}
