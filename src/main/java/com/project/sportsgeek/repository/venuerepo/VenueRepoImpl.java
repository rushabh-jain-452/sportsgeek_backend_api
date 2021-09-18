package com.project.sportsgeek.repository.venuerepo;

import com.project.sportsgeek.mapper.VenueRowMapper;
import com.project.sportsgeek.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "venueRepo")
public class VenueRepoImpl implements VenueRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Venue> findAllVenue() {
        String sql = "SELECT * FROM Venue";
        return jdbcTemplate.query(sql,new VenueRowMapper());
    }

    @Override
    public Venue findVenueById(int venueId) throws Exception {
        String sql = "SELECT * FROM Venue WHERE VenueId=:venueId";
        MapSqlParameterSource params = new MapSqlParameterSource("venueId", venueId);
        List<Venue> venueList = jdbcTemplate.query(sql, params, new VenueRowMapper());
        if(venueList.size() > 0){
            return venueList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int addVenue(Venue venue) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Venue(Name) VALUES(:name)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(venue), holder);
        if(n > 0) {
            return holder.getKey().intValue();
        }else {
            return 0;
        }
    }

    @Override
    public boolean updateVenue(int venueId, Venue venue) throws Exception {
        String sql = "UPDATE Venue set Name = :name where VenueId = :venueId";
        venue.setVenueId(venueId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(venue)) > 0;
    }

    @Override
    public boolean deleteVenue(int venueId) throws Exception {
        String sql = "DELETE FROM Venue WHERE VenueId =:venueId";
        MapSqlParameterSource params = new MapSqlParameterSource("venueId", venueId);
        return  jdbcTemplate.update(sql, params) > 0;
    }
}
