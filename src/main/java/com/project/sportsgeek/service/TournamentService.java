package com.project.sportsgeek.service;


import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Tournament;
import com.project.sportsgeek.repository.tournamentrepo.TournamentRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TournamentService {

    @Autowired
    @Qualifier("tournamentRepo")
    TournamentRepository tournamentRepository;

    public Result<List<Tournament>> findAllTournament() {
        List<Tournament> tournamentList = tournamentRepository.findAllTournament();
        return new Result<>(200,"Tournament Details Retrieved Successfully",tournamentList);
    }

    public Result<Tournament> findTournamentById(int tournamentId) throws Exception{
        Tournament tournament = tournamentRepository.findTournamentById(tournamentId);
        if (tournament != null) {
            return new Result<>(200,"Tournament Details Retrieved Successfully", tournament);
        }
        throw new ResultException(new Result<>(404, "Tournament with TournamentId: " + tournamentId + " not found."));
    }

    public Result<Tournament> findTournamentByActive() throws Exception{
        Tournament tournament = tournamentRepository.findTournamentByActive();
        if (tournament != null) {
            return new Result<>(200,"Active Tournament Details Retrieved Successfully", tournament);
        }
        throw new ResultException(new Result<>(404, "No Active Tournament's found,please try again."));
    }

    public Result<Tournament> addTournament(Tournament tournament) throws Exception {
        int id = tournamentRepository.addTournament(tournament);
        if (id > 0) {
            tournament.setTournamentId(id);
            return new Result<>(201,"Tournament Added Successfully",tournament);
        }
        throw new ResultException(new Result<>(404, "Unable to add Tournament."));
    }

    public Result<Tournament> updateTournament(int tournamentId, Tournament tournament) throws Exception {
        if (tournamentRepository.updateTournament(tournamentId,tournament)) {
            return new Result<>(200,"Tournament Updated Successfully",tournament);
        }
        throw new ResultException(new Result<>(404, "Tournament with TournamentId: " + tournamentId + " not found."));
    }

    public Result<String> updateActiveTournament(int tournamentId) throws Exception {
        if(tournamentRepository.deactivateTournament())
        {
            if (tournamentRepository.updateActiveTournament(tournamentId)) {
                return new Result<>(200, "Tournament Activated Successfully");
            }
            else
            {
                return new Result<>(404, "No Tournament's found to Activate ,please try again");
            }
        }
        else
        {
            return new Result<>(404, "Deactivation Failed of tournament ,please try again");
        }
    }

    public Result<String> deleteTournament(int tournamentId) throws Exception{
        if (tournamentRepository.deleteTournament(tournamentId)) {
            return new Result<>(200, "Tournament deleted Successfully");
        }
        throw new ResultException(new Result<>(404, "Tournament with TournamentId: " + tournamentId + " not found."));
    }
}
