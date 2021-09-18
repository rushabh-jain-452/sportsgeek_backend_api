package com.project.sportsgeek.service;


import com.project.sportsgeek.model.Contest;
import com.project.sportsgeek.model.Statistics;
import com.project.sportsgeek.repository.statisticsrepo.StatisticsRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    @Qualifier("statRepo")
    StatisticsRepository statisticsRepository;

    public Result<List<Statistics>> findAllStatistics() {
        List<Statistics> statList = statisticsRepository.findUserStatistics();
        return new Result<>(200, statList);
    }
    public Result<List<Contest>> findFutureContests() {
        List<Contest> contestList = statisticsRepository.findFutureContestPoints();
        return new Result<>(200, contestList);
    }
}
