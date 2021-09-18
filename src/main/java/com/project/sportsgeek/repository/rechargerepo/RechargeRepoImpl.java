package com.project.sportsgeek.repository.rechargerepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.project.sportsgeek.mapper.RechargeRowMapper;
import com.project.sportsgeek.model.Recharge;

@Repository(value = "rechargeRepo")
public class RechargeRepoImpl implements RechargeRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Recharge> findAllRecharge() {
        String sql = "SELECT Recharge.UserId as UserId,UserName,Points,RechargeDatetime,RechargeId FROM Recharge Inner Join User on User.UserId = Recharge.UserId";
        return jdbcTemplate.query(sql,new RechargeRowMapper());
    }

    @Override
    public Recharge findRechargeByRechargeId(int rechargeId) throws Exception {
        String sql = "SELECT Recharge.UserId as UserId,UserName,Points,RechargeDatetime,RechargeId FROM Recharge Inner Join User on User.UserId = Recharge.UserId WHERE RechargeId = :rechargeId";
        MapSqlParameterSource params = new MapSqlParameterSource("rechargeId", rechargeId);
        List<Recharge> rechargeList = jdbcTemplate.query(sql, params, new RechargeRowMapper());
        if(rechargeList.size() > 0){
            return rechargeList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<Recharge> findRechargeByUserId(int userId) throws Exception {
        String sql = "SELECT Recharge.UserId as UserId,UserName,Points,RechargeDatetime,RechargeId FROM Recharge Inner Join User on User.UserId = Recharge.UserId WHERE Recharge.UserId = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.query(sql, params, new RechargeRowMapper());
    }

    @Override
    public int addRecharge(Recharge recharge) throws Exception {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            String sql = "INSERT INTO Recharge(UserId, Points) VALUES(:userId, :points)";
            int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(recharge), holder);
            if(n > 0) {
                return holder.getKey().intValue();
            }else {
                return 0;
            }
        }catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean updateRecharge(int rechargeId, Recharge recharge) throws Exception {
        try{
            recharge.setRechargeId(rechargeId);
            String sql = "UPDATE Recharge set UserId = :userId, Points = :points where RechargeId = :rechargeId";
            return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(recharge)) > 0;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteRecharge(int rechargeId) throws Exception {
        String sql = "DELETE FROM Recharge WHERE RechargeId = :rechargeId";
        MapSqlParameterSource params = new MapSqlParameterSource("rechargeId", rechargeId);
        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean deleteRechargeByUserId(int userId) throws Exception {
        String sql = "DELETE FROM Recharge WHERE UserId = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.update(sql, params) > 0;
    }

}