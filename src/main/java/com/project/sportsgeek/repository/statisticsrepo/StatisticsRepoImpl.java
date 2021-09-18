package com.project.sportsgeek.repository.statisticsrepo;


import com.project.sportsgeek.mapper.FutureContestsRowMapper;
import com.project.sportsgeek.mapper.StatisticsRowMapper;
import com.project.sportsgeek.model.Contest;
import com.project.sportsgeek.model.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "statRepo")
public class StatisticsRepoImpl implements  StatisticsRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<Statistics> findUserStatistics() {
//        String sql = "select u.UserId,FirstName,LastName,UserName,sum(WinningPoints) as TotalWinningPoints " +
//                "from Contest as bot inner join Users as u on bot.UserId=u.UserId group by bot.UserId" +
//                " order by TotalWinningPoints desc";
//        String sql = "SELECT UserId, FirstName, LastName, UserName,AvailablePoints as TotalWinningPoints FROM Users ORDER BY AvailablePoints DESC";

        String sql = "SELECT u.UserId as UserId, FirstName, LastName, UserName, ProfilePicture, AvailablePoints " +
                "FROM Users as u LEFT JOIN Contest as c ON u.UserId=c.UserId WHERE u.Status=TRUE " +
                "GROUP BY u.UserId ORDER BY AvailablePoints DESC";
        return jdbcTemplate.query(sql, new StatisticsRowMapper());
    }

    @Override
    public List<Contest> findFutureContestPoints() {
//        String sql = "SELECT ContestPoints FROM Contest WHERE UserId="+userId+" AND ResultStatus IS NULL "
        String sql = "SELECT UserId, SUM(ContestPoints) as TotalContestPoints\n" +
                "FROM Contest as c inner join Matches as m on c.MatchId=m.MatchId\n" +
                "WHERE ResultStatus IS NULL \n" +
                "GROUP BY UserId ORDER BY UserId";
       return jdbcTemplate.query(sql , new FutureContestsRowMapper());
    }
}
