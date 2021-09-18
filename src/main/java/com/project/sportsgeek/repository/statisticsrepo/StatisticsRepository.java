package com.project.sportsgeek.repository.statisticsrepo;

import com.project.sportsgeek.model.Contest;
import com.project.sportsgeek.model.Statistics;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "statRepo")
public interface StatisticsRepository {

    List<Statistics> findUserStatistics();

    List<Contest> findFutureContestPoints();

}
