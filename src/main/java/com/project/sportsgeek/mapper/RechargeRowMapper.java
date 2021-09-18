package com.project.sportsgeek.mapper;

import com.project.sportsgeek.model.Recharge;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RechargeRowMapper implements RowMapper<Recharge> {

    @Override
    public Recharge mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recharge recharge = new Recharge();
        recharge.setRechargeId(rs.getInt("RechargeId"));
        recharge.setUserId(rs.getInt("UserId"));
        recharge.setUsername(rs.getString("UserName"));
        recharge.setPoints(rs.getInt("Points"));
        recharge.setRechargeDate(rs.getTimestamp("RechargeDatetime"));
        return recharge;
    }
}
