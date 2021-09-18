package com.project.sportsgeek.service;

import com.project.sportsgeek.model.MyMatches;
import com.project.sportsgeek.repository.mymatchesrepo.MyMatchesRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyMatchesService {

    @Autowired
    @Qualifier("myMatchesRepo")
    MyMatchesRepository myMatchesRepository;

    public Result<List<MyMatches>> findUpcomingMatchesByUserId(int userId) throws Exception {
        List<MyMatches> matchesList = myMatchesRepository.findUpcomingContestByUserId(userId);
        return new Result<>(200, matchesList);
    }

    public Result<List<MyMatches>> findLiveMatchesByUserId(int userId) throws Exception {
        List<MyMatches> matchesList = myMatchesRepository.findLiveContestByUserId(userId);
        return new Result<>(200, matchesList);
    }

    public Result<List<MyMatches>> findResultMatchesByUserId(int userId) throws Exception {
        List<MyMatches> matchesList = myMatchesRepository.findResultContestByUserId(userId);
        return new Result<>(200, matchesList);
    }
}
