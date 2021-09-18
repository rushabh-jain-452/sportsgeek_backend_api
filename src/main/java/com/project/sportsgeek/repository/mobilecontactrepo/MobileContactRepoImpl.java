package com.project.sportsgeek.repository.mobilecontactrepo;

import com.project.sportsgeek.model.MobileContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "mobileContactRepo")
public class MobileContactRepoImpl implements MobileContactRepository{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<MobileContact> findAllMobileContact() {
        return null;
    }

    @Override
    public MobileContact findMobileContactById(int mobileContactId) throws Exception {
        return null;
    }

    @Override
    public int addMobileContact(MobileContact mobileContact) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO MobileContact (UserId, MobileNumber) values(:userId, :mobileNumber)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(mobileContact), holder);
        if(n > 0 && holder.getKeys().size() > 0) {
//            return holder.getKey().intValue();
            return (int)holder.getKeys().get("MobileContactId");
        }else {
            return 0;
        }
    }

    @Override
    public boolean updateMobileContact(int mobileContactId, MobileContact mobileContact) throws Exception {
        String sql = "UPDATE MobileContact SET UserId = :userId, MobileNumber = :mobileNumber WHERE MobileContactId = :mobileContactId";
        mobileContact.setMobileContactId(mobileContactId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(mobileContact)) > 0;
    }

    @Override
    public boolean updateMobileContactByUserId(int userId, MobileContact mobileContact) throws Exception {
        String sql = "UPDATE MobileContact SET MobileNumber = :mobileNumber WHERE UserId = :userId";
        mobileContact.setUserId(userId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(mobileContact)) > 0;
    }

    @Override
    public boolean deleteMobileContact(int mobileContactId) throws Exception {
        String sql = "DELETE FROM MobileContact WHERE MobileContactId = :mobileContactId";
        MapSqlParameterSource params = new MapSqlParameterSource("mobileContactId", mobileContactId);
        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean deleteMobileContactByUserId(int userId) throws Exception {
        String sql = "DELETE FROM MobileContact WHERE UserId = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.update(sql, params) > 0;
    }
}
