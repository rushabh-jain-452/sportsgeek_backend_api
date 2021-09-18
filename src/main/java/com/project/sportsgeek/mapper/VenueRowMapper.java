package com.project.sportsgeek.mapper;

import com.project.sportsgeek.model.Venue;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VenueRowMapper implements RowMapper<Venue> {
    @Override
    public Venue mapRow(ResultSet rs, int rowNum) throws SQLException {
        Venue venue = new Venue();
        venue.setVenueId(rs.getInt("VenueId"));
        venue.setName(rs.getString("Name"));
        return venue;
    }
}
