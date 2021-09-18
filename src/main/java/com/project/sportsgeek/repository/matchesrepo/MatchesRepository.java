package com.project.sportsgeek.repository.matchesrepo;


import com.project.sportsgeek.model.Matches;
import com.project.sportsgeek.model.MatchesWithVenue;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository(value = "matchesRepo")
public interface MatchesRepository {

    List<MatchesWithVenue> findAllMatches(int tournamentId) throws Exception;

    List<MatchesWithVenue> findAllUpcomingMatches(int tournamentId) throws Exception;

    MatchesWithVenue findMatchById(int matchId) throws Exception;

    List<MatchesWithVenue> findAllMatchesByTournament(int tournamentId) throws Exception;

    List<MatchesWithVenue> findAllMatchesByVenue(int venueId) throws Exception;

    List<MatchesWithVenue> findAllMatchesByTeam(int teamId) throws Exception;

    List<MatchesWithVenue> findAllMatchesByPreviousDateAndResultStatus(int tournamentId) throws Exception;

    List<MatchesWithVenue> findAllMatchesByMinimumPoints(int minPoints) throws Exception;

    int addMatch(Matches matches) throws Exception;

    boolean updateMatch(int matchId, Matches matches) throws Exception;

    boolean updateMatchWinningTeam(int matchId, int ResultStatus, int winningTeamId) throws Exception;

    boolean updateMatchVenue(int matchId, int venueId) throws Exception;

    boolean updateResultStatus(int matchId, boolean status) throws Exception;

    boolean updateMinimumPoints(int matchId, int minPoints) throws Exception;

    boolean updateMatchScheduleDate(int matchId, Timestamp date) throws Exception;

    boolean deleteMatches(int matchId) throws Exception;

    Timestamp getMatchStartDatetimeById(int matchId);

    Timestamp getCurrentTimestamp();

}
