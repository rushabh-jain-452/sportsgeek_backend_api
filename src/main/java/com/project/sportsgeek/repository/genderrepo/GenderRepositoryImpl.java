package com.project.sportsgeek.repository.genderrepo;

import com.project.sportsgeek.mapper.GenderRowMapper;
import com.project.sportsgeek.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "genderRepo")
public class GenderRepositoryImpl implements GenderRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Gender> findAllGender() {
        String sql = "SELECT * FROM Gender";
        return jdbcTemplate.query(sql, new GenderRowMapper());
    }

    @Override
    public Gender findGenderById(int genderId) throws Exception {
        String sql = "SELECT * FROM Gender WHERE GenderId = :genderId";
        MapSqlParameterSource params = new MapSqlParameterSource("genderId", genderId);
        List<Gender> genderList = jdbcTemplate.query(sql, params, new GenderRowMapper());
        if(genderList.size() > 0){
            return genderList.get(0);
        }
        return null;
    }

    @Override
    public int addGender(Gender gender) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Gender (Name) values(:name)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(gender), holder);
        if(n > 0) {
            return holder.getKey().intValue();
        }
        return 0;
    }

    @Override
    public boolean updateGender(int genderId, Gender gender) throws Exception {
        String sql = "UPDATE Gender SET Name = :name WHERE GenderId = :genderId";
        gender.setGenderId(genderId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(gender)) > 0;
    }

    @Override
    public boolean deleteGender(int genderId) throws Exception {
        String sql = "DELETE FROM Gender WHERE GenderId = :genderId";
        MapSqlParameterSource params = new MapSqlParameterSource("genderId", genderId);
        return jdbcTemplate.update(sql, params) > 0;
    }

}
