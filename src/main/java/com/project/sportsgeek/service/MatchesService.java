package com.project.sportsgeek.service;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Matches;
import com.project.sportsgeek.model.MatchesWithVenue;
import com.project.sportsgeek.model.Player;
import com.project.sportsgeek.model.Tournament;
import com.project.sportsgeek.repository.matchesrepo.MatchesRepository;
import com.project.sportsgeek.repository.tournamentrepo.TournamentRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MatchesService {
    @Autowired
    @Qualifier("matchesRepo")
    MatchesRepository matchesRepository;

    @Autowired
    @Qualifier("tournamentRepo")
    TournamentRepository tournamentRepository;

    public Result<List<MatchesWithVenue>> findAllMatches() throws Exception {
        Tournament tournament = tournamentRepository.findTournamentByActive();
        if(tournament != null)
        {
            List<MatchesWithVenue> matchesList = matchesRepository.findAllMatches(tournament.getTournamentId());
            return new Result<>(200, matchesList);
        }
        throw new ResultException(new Result<>(404, "Unable to find the Active Tournament."));
    }

    public Result<List<MatchesWithVenue>> findAllUpcomingMatches() throws Exception {
        Tournament tournament = tournamentRepository.findTournamentByActive();
        if(tournament != null)
        {
            List<MatchesWithVenue> matchesList = matchesRepository.findAllUpcomingMatches(tournament.getTournamentId());
            return new Result<>(200, matchesList);
        }
        throw new ResultException(new Result<>(404, "Unable to find the Active Tournament."));
    }
    
    public Result<MatchesWithVenue> findMatchesById(int matchId) throws Exception {
        MatchesWithVenue matchesWithVenue = matchesRepository.findMatchById(matchId);
        if(matchesWithVenue != null){
            return new Result<>(200, matchesWithVenue);
        }
        throw new ResultException(new Result<>(404, "Match with MatchId: " + matchId + " not found."));
    }

    public Result<List<MatchesWithVenue>> findMatchesByTournament(int tournamentId) throws Exception {
        List<MatchesWithVenue> matchesList = matchesRepository.findAllMatchesByTournament(tournamentId);
        return new Result<>(200, matchesList);
    }

    public Result<List<MatchesWithVenue>> findMatchesByVenue(int venueId) throws Exception {
        List<MatchesWithVenue> matchesList = matchesRepository.findAllMatchesByVenue(venueId);
        return new Result<>(200, matchesList);
    }

    public Result<List<MatchesWithVenue>> findMatchesByTeam(int teamId) throws Exception {
        List<MatchesWithVenue> matchesList = matchesRepository.findAllMatchesByTeam(teamId);
        return new Result<>(200, matchesList);
    }

    public Result<List<MatchesWithVenue>> findMatchesByMinPoints(int minPoints) throws Exception {
        List<MatchesWithVenue> matchesList = matchesRepository.findAllMatchesByMinimumPoints(minPoints);
        return new Result<>(200, matchesList);
    }

    public Result<List<MatchesWithVenue>> findAllMatchesByPreviousDateAndResultStatus() throws Exception {
        Tournament tournament = tournamentRepository.findTournamentByActive();
        List<MatchesWithVenue> matchesList = matchesRepository.findAllMatchesByPreviousDateAndResultStatus(tournament.getTournamentId());
        return new Result<>(200,matchesList);
    }

    public Result<Matches> addMatches(Matches matches) throws Exception {
        int matchId = matchesRepository.addMatch(matches);
        if(matchId > 0){
            matches.setMatchId(matchId);
            return new Result<>(201, matches);
        }
        throw new ResultException(new Result<>(404, "Unable to add Match"));
    }

    public Result<Matches> updateMatch(int matchId, Matches matches) throws Exception {
        if (matchesRepository.updateMatch(matchId, matches)) {
            return new Result<>(200, matches);
        }
        throw new ResultException(new Result<>(404, "Match with MatchId: " + matchId + " not found."));
    }

    public Result<String> updateMatchWinningTeam(int matchId, int ResultStatus, int winningTeamId) throws Exception {
        if(matchesRepository.updateMatchWinningTeam(matchId, ResultStatus, winningTeamId)== true) {
            return new Result<>(200, "Match Result updated successfully!!");
        }
        throw new ResultException(new Result<>(404, "Failed to Update Match Result!!"));
    }

    public Result<String> updateMinimumPoints(int matchId, int minPoints) throws Exception {
        if(matchesRepository.updateMinimumPoints(matchId, minPoints)) {
            return new Result<>(200, "Successfully Updated Minimum Points for Match");
        }
        throw new ResultException(new Result<>(500, "Internal Server error!, Unable to update the Minimum Points"));
    }

    public Result<String> updateMatchVenue(int matchId, int venueId) throws Exception {
        if(matchesRepository.updateMatchVenue(matchId, venueId)) {
            return new Result<>(200, "Successfully Updated Venue for Match");
        }
        throw new ResultException(new Result<>(500, "Internal Server error!, Unable to update the Venue"));
    }

    public Result<String> updateMatchResultStatus(int matchId, boolean status) throws Exception {
        if(matchesRepository.updateResultStatus(matchId, status)) {
            return new Result<>(200, "Successfully Updated Result Status for Match");
        }
        throw new ResultException(new Result<>(500, "Internal Server error!, Unable to update the Result Status"));
    }

    public Result<String> updateMatchStartDateTime(int matchId, Timestamp date) throws Exception {
        if(matchesRepository.updateMatchScheduleDate(matchId, date)) {
            return new Result<>(200, "Successfully Updated Match StartDateTime");
        }
        throw new ResultException(new Result<>(500, "Internal Server error!, Unable to update the Match StartDateTime"));
    }

    public Result<String> deleteMatch(int matchId) throws Exception{
        if (matchesRepository.deleteMatches(matchId)) {
            return new Result<>(200,"Match Deleted Successfully");
        }
        throw new ResultException((new Result<>(404,"No Match found to delete,please try again")));
    }
}
