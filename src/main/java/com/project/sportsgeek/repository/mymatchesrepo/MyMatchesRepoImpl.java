package com.project.sportsgeek.repository.mymatchesrepo;

import com.project.sportsgeek.mapper.MyMatchesResultRowMapper;
import com.project.sportsgeek.mapper.MyMatchesRowMapper;
import com.project.sportsgeek.model.MyMatches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "myMatchesRepo")
public class MyMatchesRepoImpl implements MyMatchesRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<MyMatches> findUpcomingContestByUserId(int userId) throws Exception {
       String sql = " SELECT StartDatetime, t3.ShortName as TeamName, ContestPoints,c.MatchId as MatchId, t1.ShortName as Team1Short, t1.TeamLogo as Team1Logo, t2.ShortName as Team2Short, t2.TeamLogo as Team2Logo, v.Name as Venue\n" +
               "        FROM Matches as m inner join Team as t1 on m.Team1=t1.TeamId\n" +
               "        inner join Team as t2 on m.Team2=t2.TeamId\n" +
               "        inner join Contest as c on c.MatchId = m.MatchId\n" +
               "        inner join Team as t3 on c.TeamId = t3.TeamId\n" +
               "        inner join Venue as v on m.VenueId = v.VenueId WHERE m.StartDateTime > CURRENT_TIMESTAMP and c.UserId = :userId order by StartDatetime";
       MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
       return jdbcTemplate.query(sql, params, new MyMatchesRowMapper());
    }

    @Override
    public List<MyMatches> findLiveContestByUserId(int userId) throws Exception {
        String countSql = "SELECT COUNT(*) FROM Contest WHERE UserId=:userId AND MatchId IN (SELECT MatchId FROM Matches WHERE StartDatetime <= current_timestamp and ResultStatus IS NULL);";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        int count = jdbcTemplate.queryForObject(countSql, params, Integer.class);
        if(count > 0){
            String sql = " SELECT StartDatetime, t3.ShortName as TeamName, c.MatchId as MatchId, ContestPoints, t1.ShortName as Team1Short, t1.TeamLogo as Team1Logo, t2.ShortName as Team2Short, t2.TeamLogo as Team2Logo, v.Name as Venue\n" +
                    "        FROM Matches as m inner join Team as t1 on m.Team1=t1.TeamId\n" +
                    "        inner join Team as t2 on m.Team2=t2.TeamId\n" +
                    "        inner join Contest as c on c.MatchId = m.MatchId\n" +
                    "        inner join Team as t3 on c.TeamId = t3.TeamId\n" +
                    "        inner join Venue as v on m.VenueId = v.VenueId WHERE date(m.StartDatetime) = current_date and StartDatetime <= current_timestamp  and ResultStatus IS NULL and c.UserId=:userId order by StartDatetime DESC";
//        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
            return jdbcTemplate.query(sql, params, new MyMatchesRowMapper());
        } else {
            String sql = "SELECT StartDatetime, m.MatchId as MatchId, t1.ShortName as Team1Short, t1.TeamLogo as Team1Logo, t2.ShortName as Team2Short, t2.TeamLogo as Team2Logo, v.Name as Venue, 'NA' AS TeamName, 0 AS ContestPoints\n" +
                    "FROM Matches as m inner join Team as t1 on m.Team1=t1.TeamId\n" +
                    "inner join Team as t2 on m.Team2=t2.TeamId\n" +
                    "inner join Venue as v on m.VenueId = v.VenueId \n" +
                    "WHERE date(m.StartDatetime) = current_date and StartDatetime <= current_timestamp and ResultStatus IS NULL order by StartDatetime DESC;";
            return jdbcTemplate.query(sql, params, new MyMatchesRowMapper());
        }
    }

    @Override
    public List<MyMatches> findResultContestByUserId(int userId) throws Exception {
        String sql = " SELECT StartDatetime, t3.ShortName as TeamName, ContestPoints, t1.ShortName as Team1Short, t1.TeamLogo as Team1Logo, t2.ShortName as Team2Short, t2.TeamLogo as Team2Logo, v.Name as Venue, WinningPoints, t4.ShortName as WinnerTeam, ResultStatus, c.MatchId as MatchId \n" +
                "        FROM Matches as m inner join Team as t1 on m.Team1=t1.TeamId\n" +
                "        inner join Team as t2 on m.Team2=t2.TeamId\n" +
                "        inner join Contest as c on c.MatchId = m.MatchId\n" +
                "        inner join Team as t3 on c.TeamId = t3.TeamId\n" +
                "        left join Team as t4 on m.WinnerTeamId = t4.TeamId" +
                "        inner join Venue as v on m.VenueId = v.VenueId WHERE m.StartDateTime < CURRENT_TIMESTAMP and ResultStatus IS NOT NULL and c.UserId = :userId order by StartDatetime DESC";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query(sql, params, new MyMatchesResultRowMapper());
    }
}
