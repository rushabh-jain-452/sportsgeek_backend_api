package com.project.sportsgeek.repository.contestrepo;

import java.util.List;

import com.project.sportsgeek.mapper.ContestRowMapper;
import com.project.sportsgeek.mapper.GenderRowMapper;
import com.project.sportsgeek.model.Contest;
import com.project.sportsgeek.model.ContestWithResult;
import com.project.sportsgeek.model.ContestWithUser;
import com.project.sportsgeek.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.project.sportsgeek.mapper.ContestWithMatchRowMapper;
import com.project.sportsgeek.mapper.ContestWithUsersResultRowMapper;

@Repository(value = "contestRepo")
public class ContestRepoImpl implements ContestRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Contest findContestByUserAndMatch(int userId, int matchId) throws Exception {
        String sql = "SELECT ContestId, UserId, MatchId, TeamId, ContestPoints, WinningPoints from Contest WHERE UserId = :userId and MatchId = :matchId";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        params.addValue("matchId", matchId);
        List<Contest> contestList = jdbcTemplate.query(sql, params, new ContestWithMatchRowMapper());
        if (contestList.size() > 0) {
            return contestList.get(0);
        }
        return null;
    }

    @Override
    public Contest findContestById(int contestId) throws Exception {
        String sql = "SELECT ContestId, UserId, MatchId, TeamId, ContestPoints, WinningPoints from Contest WHERE ContestId = :contestId";
        MapSqlParameterSource params = new MapSqlParameterSource("contestId", contestId);
        List<Contest> contestList = jdbcTemplate.query(sql, params, new ContestWithMatchRowMapper());
        if (contestList.size() > 0) {
            return contestList.get(0);
        }
        return null;
    }

    @Override
    public List<ContestWithUser> findAllContestByMatchId(int matchId) throws Exception {
        String sql = "SELECT ContestId, c.UserId as UserId, MatchId, c.TeamId as TeamId, t.ShortName as TeamShortName, FirstName, LastName, Username, ProfilePicture, ContestPoints, WinningPoints " +
                "FROM Contest as c, Users as u, Team as t " +
                "WHERE c.TeamId=t.TeamId AND c.UserId=u.UserId AND MatchId = :matchId";
        MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);
        return jdbcTemplate.query(sql, params, new ContestRowMapper());
//        List<ContestWithUser> contestOnTeamWithUsers = jdbcTemplate.query(sql, params, new ContestRowMapper());
//        System.out.println(contetsOnTeamWithUsers);
//        return contestOnTeamWithUsers;
    }

    @Override
    public List<ContestWithResult> findContestResultByMatchId(int matchId) throws Exception {
        String sql = "SELECT ContestId, t.ShortName as TeamShortName, FirstName, LastName, Username, ProfilePicture, ContestPoints, WinningPoints " +
                "FROM Contest as c inner join Users as u on c.UserId=u.UserId inner join Team as t on c.TeamId=t.TeamId " +
                "WHERE MatchId = :matchId AND ContestPoints > 0 " +
                "ORDER BY WinningPoints desc, ContestPoints desc, t.ShortName";
        MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);
        return jdbcTemplate.query(sql, params, new ContestWithUsersResultRowMapper());
    }

    @Override
    public int addContest(Contest contest) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
//        String sql = "INSERT INTO Contest (UserId, MatchId, TeamId, ContestPoints, WinningPoints) VALUES (:userId, :matchId, :teamId, :contestPoints, :winningPoints)";
        String sql = "INSERT INTO Contest (UserId, MatchId, TeamId, ContestPoints) VALUES (:userId, :matchId, :teamId, :contestPoints)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(contest), holder);
//        public int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder, @Nullable String[] keyColumnNames)
//        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(contest), holder, new String[]{"ContestId"});
//        System.out.println(n);
//        System.out.println(holder);
        if(n > 0 && holder.getKeys().size() > 0){
//            return holder.getKey().intValue();
            return (int)holder.getKeys().get("ContestId");
//            return 1;
        }
        return 0;
    }

    @Override
    public boolean updateContest(int contestId, Contest contest) throws Exception {
        contest.setContestId(contestId);
//        String sql = "Update Contest SET TeamId = :teamId, ContestPoints = :contestPoints, UpdateTimestamp=CURRENT_TIMESTAMP WHERE ContestId = :contestId";
        // Don't update contest points
        String sql = "Update Contest SET TeamId = :teamId, UpdateTimestamp=CURRENT_TIMESTAMP WHERE ContestId = :contestId";
//        Error : column "StartDatetime" doesn't exists in Contest table
//        String sql = "Update Contest SET TeamId = :teamId, ContestPoints = :contestPoints, UpdateTimestamp=CURRENT_TIMESTAMP WHERE ContestId = :contestId AND CURRENT_TIMESTAMP <= StartDatetime";
        return jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(contest)) > 0;
    }

    @Override
    public int getContestPoints(int contestId) throws Exception {
        String sql = "SELECT ContestId, UserId, MatchId, TeamId, ContestPoints, WinningPoints FROM Contest WHERE ContestId = :contestId";
        MapSqlParameterSource params = new MapSqlParameterSource("contestId", contestId);
        return jdbcTemplate.query(sql, params, new ContestWithMatchRowMapper()).get(0).getContestPoints();
    }

    @Override
    public boolean deleteContestById(int contestId) throws Exception {
        String sql = "DELETE FROM Contest WHERE ContestId = :contestId";
        MapSqlParameterSource params = new MapSqlParameterSource("contestId", contestId);
        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean deleteContestsByUserId(int userId) throws Exception {
        String sql = "DELETE FROM Contest WHERE UserId = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.update(sql, params) > 0;
    }

}