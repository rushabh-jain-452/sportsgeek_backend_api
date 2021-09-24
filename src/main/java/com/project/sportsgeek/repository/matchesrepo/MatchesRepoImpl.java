package com.project.sportsgeek.repository.matchesrepo;

import com.project.sportsgeek.mapper.MatchesRowMapper;
import com.project.sportsgeek.mapper.TournamentRowMapper;
import com.project.sportsgeek.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository(value = "matchesRepo")
public class MatchesRepoImpl implements MatchesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Override
    public List<MatchesWithVenue> findAllMatches(int tournamentId) throws Exception {
       String sql = "SELECT MatchId, StartDatetime, m.Name as Name, Team1, Team2, t1.Name as team1long, t1.ShortName as team1short, " +
               "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, m.venueId as venueId, v.Name as venue, MinimumPoints, WinnerTeamId, ResultStatus, TournamentId  " +
               "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
               "order by StartDatetime";
       MapSqlParameterSource params = new MapSqlParameterSource("tournamentId", tournamentId);
       return namedParameterJdbcTemplate.query(sql, params, new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllUpcomingMatches(int tournamentId) throws Exception {
        String sql = "SELECT MatchId, StartDatetime, m.Name as Name, Team1, Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, m.venueId as venueId, v.Name as venue, MinimumPoints, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where TournamentId=:tournamentId and StartDatetime > CURRENT_TIMESTAMP order by StartDatetime";
        MapSqlParameterSource params = new MapSqlParameterSource("tournamentId", tournamentId);
        return namedParameterJdbcTemplate.query(sql, params, new MatchesRowMapper());
    }

    @Override
    public MatchesWithVenue findMatchById(int matchId) throws Exception {
//		String tournament_sql = "SELECT * from Tournament WHERE active = true";
//        int tournamentid = jdbcTemplate.query(tournament_sql,new TournamentRowMapper()).get(0).getTournamentId();
        String sql = "SELECT MatchId, StartDatetime, m.Name as Name, Team1, Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, m.venueId as venueId, v.Name as venue, MinimumPoints, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where MatchId=:matchId";
        MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);
        List<MatchesWithVenue> matchList = namedParameterJdbcTemplate.query(sql, params, new MatchesRowMapper());
        if(matchList.size() > 0){
            return matchList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByTournament(int tournamentId) throws Exception {
        String sql = "SELECT MatchId, StartDatetime, m.Name as Name, Team1, Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, m.venueId as venueId, v.Name as venue, MinimumPoints, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where TournamentId=:tournamentId ORDER BY MatchId";
        MapSqlParameterSource params = new MapSqlParameterSource("tournamentId", tournamentId);
        return namedParameterJdbcTemplate.query(sql, params, new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByVenue(int venueId) throws Exception {
        String sql = "SELECT MatchId, StartDatetime, m.Name as Name, Team1, Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, m.venueId as venueId, v.Name as venue, MinimumPoints, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where m.VenueId=:venueId ORDER BY MatchId";
        MapSqlParameterSource params = new MapSqlParameterSource("venueId", venueId);
        return namedParameterJdbcTemplate.query(sql, params, new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByTeam(int teamId) throws Exception {
        String sql = "SELECT MatchId, StartDatetime, m.Name as Name, Team1, Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, m.venueId as venueId, v.Name as venue, MinimumPoints, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where m.Team1=:teamId or m.Team2=:teamId ORDER BY MatchId";
        MapSqlParameterSource params = new MapSqlParameterSource("teamId", teamId);
        return namedParameterJdbcTemplate.query(sql, params, new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByPreviousDateAndResultStatus(int tournamentId) throws Exception {
        String sql = "SELECT MatchId, StartDatetime, m.Name as Name, Team1, Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, m.venueId as venueId, v.Name as venue, MinimumPoints, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where TournamentId=:tournamentId and StartDatetime < CURRENT_TIMESTAMP and m.ResultStatus IS NULL order by StartDatetime";
//                "where TournamentId=:tournamentId and StartDatetime < CURRENT_TIMESTAMP and m.ResultStatus IS NOT NULL order by StartDatetime";
        MapSqlParameterSource params = new MapSqlParameterSource("tournamentId", tournamentId);
        return namedParameterJdbcTemplate.query(sql, params, new MatchesRowMapper());
    }

    @Override
    public List<MatchesWithVenue> findAllMatchesByMinimumPoints(int minPoints) throws Exception {
        String sql = "SELECT MatchId, StartDatetime, m.Name as Name, Team1, Team2, t1.Name as team1long, t1.ShortName as team1short, " +
                "t1.TeamLogo as team1logo, t2.Name as team2long, t2.ShortName as team2short, t2.TeamLogo as team2logo, m.venueId as venueId, v.Name as venue, MinimumPoints, WinnerTeamId, ResultStatus, TournamentId  " +
                "FROM Matches as m INNER JOIN Venue as v on m.VenueId=v.VenueId left JOIN Team as t1 on m.Team1=t1.TeamId left JOIN Team as t2 on m.Team2=t2.TeamId " +
                "where m.MinimumPoints = :minPoints";
        MapSqlParameterSource params = new MapSqlParameterSource("minPoints", minPoints);
        return namedParameterJdbcTemplate.query(sql, params, new MatchesRowMapper());
    }

    @Override
    public int addMatch(Matches matches) throws Exception {
        String sql = "INSERT INTO Matches (MatchId,TournamentId,Name,StartDatetime,VenueId,Team1,Team2,MinimumPoints) VALUES(:matchId,:tournamentId,:name,:startDatetime,:venueId,:team1,:team2,:minimumPoints)";
        int n = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(matches));
        if(n > 0) {
            return matches.getMatchId();
        }
        return 0;
    }

    @Override
    public boolean updateMatch(int matchId, Matches matches) throws Exception {
//        String sql = "UPDATE Matches SET TournamentId = :tournamentId, Name = :name, StartDatetime = :startDatetime, VenueId=:venueId, Team1=:team1, Team2=:team2, WinnerTeamId=:winnerTeamId, ResultStatus=:resultStatus,MinimumPoints=:MinimumPoints WHERE MatchId=:matchId";
        String sql = "UPDATE Matches SET TournamentId = :tournamentId, Name = :name, StartDatetime = :startDatetime, VenueId=:venueId, Team1=:team1, Team2=:team2, MinimumPoints=:minimumPoints WHERE MatchId=:matchId";
        matches.setMatchId(matchId);
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(matches)) > 0;
    }

    // Calling Stored Procedure
//    @Override
//    public boolean updateMatchWinningTeam(int matchId, int resultStatus, int winningTeamId) throws Exception {
//        try
//        {
////            Params with name
//            SqlParameterSource params = new MapSqlParameterSource()
//                    .addValue("vMatchId", matchId)
//                    .addValue("vResultStatus",resultStatus)
//                    .addValue("vWinnerTeamId",winningTeamId);
//
//            // Params with position
////            Map<String, Object> paramsMap = new HashMap<>();
////            paramsMap.put("vMatchId", matchId);
////            paramsMap.put("vResultStatus", resultStatus);
////            paramsMap.put("vWinnerTeamId", winningTeamId);
////            SqlParameterSource parameterSource = new MapSqlParameterSource(paramsMap);
//
////            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("setMatchResult");
//            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("setmatchresult");
//            simpleJdbcCall.execute(params);
////            simpleJdbcCall.execute(parameterSource);
//            return true;
//        }catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//            return false;
//        }
//    }

//    @Override
//    public boolean updateMatchWinningTeam(int matchId, int resultStatus, int winnerTeamId) throws Exception {
//        try
//        {
//            List<SqlParameter> params = Arrays.asList(new SqlParameter(Types.NVARCHAR));
//            jdbcTemplate.call(new CallableStatementCreator() {
//                @Override
//                public CallableStatement createCallableStatement(Connection connection) throws SQLException {
//                    CallableStatement cs = connection.prepareCall("{call setMatchResult(?,?,?)}");
//                   //  Params with position
//                    cs.setInt(1, matchId);
//                    cs.setInt(2, resultStatus);
//                    cs.setInt(3, winnerTeamId);
//                    return cs;
//                }
//            }, params);
//            return true;
//        }catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//            return false;
//        }
//    }

//    @Override
//    public boolean updateMatchWinningTeam(int matchId, int resultStatus, int winningTeamId) throws Exception {
//        try
//        {
//            MyStoredProcedure myStoredProcedure = new MyStoredProcedure(jdbcTemplate, "setMatchResult");
//
//            SqlParameter param1 = new SqlParameter("vMatchId", Types.INTEGER);
//            SqlParameter param2 = new SqlParameter("vResultStatus", Types.INTEGER);
//            SqlParameter param3 = new SqlParameter("vWinnerTeamId", Types.INTEGER);
//
//            SqlParameter[] paramArray = {param1, param2, param3};
//
//            myStoredProcedure.setParameters(paramArray);
//            myStoredProcedure.compile();
//            myStoredProcedure.setJdbcTemplate(jdbcTemplate);
//
////            Map<String, Object> paramsMap = new HashMap<>();
////            paramsMap.put("vMatchId", matchId);
////            paramsMap.put("vResultStatus", resultStatus);
////            paramsMap.put("vWinnerTeamId", winningTeamId);
////            SqlParameterSource parameterSource = new MapSqlParameterSource(paramsMap);
//
////            public Map<String, Object> execute(Object... inParams)
////            public Map<String, Object> execute(Map<String, ?> inParams)
////            public Map<String, Object> execute(ParameterMapper inParamMapper)
//            myStoredProcedure.execute(matchId, resultStatus, winningTeamId);
//            return true;
//        }catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//            return false;
//        }
//    }

//    Calling Stored Function
    @Override
    public boolean updateMatchWinningTeam(int matchId, int resultStatus, int winningTeamId) throws Exception {
//        System.out.println("Setting Match Result...");
        try
        {
//            jdbcTemplate.setResultsMapCaseInsensitive(true);
//            Params with name
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("vMatchId", matchId)
                    .addValue("vResultStatus",resultStatus)
                    .addValue("vWinnerTeamId",winningTeamId);

            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("setMatchResultFun");
//            simpleJdbcCall.execute(params);
//            int n = simpleJdbcCall.executeFunction(Integer.class, params);
            simpleJdbcCall.executeFunction(Integer.class, params);
//            System.out.println("n : " + n);  // getting "null"
//            if(n == 1){
//                return true;
//            }else{
//                return false;
//            }
            return true;
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

//    @Override
//    public boolean updateMatchWinningTeam(int matchId, int resultStatus, int winningTeamId) throws Exception {
//        System.out.println("Setting Match Result...");
//        try
//        {
//            MyStoredProcedure myStoredProcedure = new MyStoredProcedure(jdbcTemplate, "setMatchResultFun");
//
//            SqlParameter param1 = new SqlParameter("vMatchId", Types.INTEGER);
//            SqlParameter param2 = new SqlParameter("vResultStatus", Types.INTEGER);
//            SqlParameter param3 = new SqlParameter("vWinnerTeamId", Types.INTEGER);
//
//            SqlParameter[] paramArray = {param1, param2, param3};
//
//            myStoredProcedure.setParameters(paramArray);
//            myStoredProcedure.compile();
//            myStoredProcedure.setJdbcTemplate(jdbcTemplate);
//
////            Map<String, Object> paramsMap = new HashMap<>();
////            paramsMap.put("vMatchId", matchId);
////            paramsMap.put("vResultStatus", resultStatus);
////            paramsMap.put("vWinnerTeamId", winningTeamId);
////            SqlParameterSource parameterSource = new MapSqlParameterSource(paramsMap);
//
////            public Map<String, Object> execute(Object... inParams)
////            public Map<String, Object> execute(Map<String, ?> inParams)
////            public Map<String, Object> execute(ParameterMapper inParamMapper)
//            myStoredProcedure.execute(matchId, resultStatus, winningTeamId);
//            return true;
//        }catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//            return false;
//        }
//    }

//    @Override
//    public boolean updateMatchWinningTeam(int matchId, int resultStatus, int winnerTeamId) throws Exception {
//        System.out.println("Setting Match Result...");
//        try
//        {
//            List<SqlParameter> params = Arrays.asList(new SqlParameter(Types.INTEGER));
//            jdbcTemplate.call(new CallableStatementCreator() {
//                @Override
//                public CallableStatement createCallableStatement(Connection connection) throws SQLException {
//                    CallableStatement cs = connection.prepareCall("{? = call setMatchResultFun(?,?,?)}");
//                    cs.registerOutParameter(1, Types.INTEGER);
//                   //  Params with position
//                    cs.setInt(2, matchId);
//                    cs.setInt(3, resultStatus);
//                    cs.setInt(4, winnerTeamId);
//                    return cs;
//                }
//            }, params);
//            return true;
//        }catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//            return false;
//        }
//    }

    @Override
    public boolean updateMatchVenue(int matchId, int venueId) throws Exception {
        String sql = "UPDATE Matches SET VenueId=:venueId WHERE MatchId=:matchId";
        MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);
        params.addValue("venueId", venueId);
        return namedParameterJdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean updateResultStatus(int matchId, boolean status) throws Exception {
        String sql = "UPDATE Matches SET ResultStatus=:resultStatus WHERE MatchId=:matchId";
        MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);
        params.addValue("status", status);
        return namedParameterJdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean updateMinimumPoints(int matchId, int minPoints) throws Exception {
       String sql = "UPDATE Matches SET MinimumPoints=:minPoints WHERE MatchId=:matchId";
       MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);
       params.addValue("minPoints", minPoints);
       return namedParameterJdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean updateMatchScheduleDate(int matchId, Timestamp date) throws Exception {
        String sql = "UPDATE Matches SET StartDatetime=:startDatetime WHERE MatchId=:matchId";
        MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);
        params.addValue("startDatetime", date);
        return namedParameterJdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean deleteMatches(int matchId) throws Exception {
       String sql = "DELETE FROM Matches WHERE MatchId=:matchId";
       MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);
       return namedParameterJdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public Timestamp getMatchStartDatetimeById(int matchId) {
        String sql = "SELECT StartDatetime FROM Matches WHERE MatchId = :matchId";
        MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);
        Timestamp matchStartDatetime = namedParameterJdbcTemplate.queryForObject(sql, params, Timestamp.class);
        return matchStartDatetime;
    }

    @Override
    public Timestamp getCurrentTimestamp() {
        String sql = "SELECT CURRENT_TIMESTAMP";
        Timestamp currentTimestamp = namedParameterJdbcTemplate.queryForObject(sql, new BeanPropertySqlParameterSource(sql), Timestamp.class);
        return currentTimestamp;
    }

}

class MyStoredProcedure extends StoredProcedure {

    public MyStoredProcedure(JdbcTemplate jdbcTemplate, String procName) {
        super(jdbcTemplate, procName);
        setFunction(false);
//        setFunction(true);
    }

}