package com.project.sportsgeek.repository.emailcontactrepo;

import com.project.sportsgeek.model.EmailContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "emailContactRepo")
public class EmailContactRepoImpl implements EmailContactRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<EmailContact> findAllEmailContact() {
        return null;
    }

    @Override
    public EmailContact findEmailContactById(int emailContactId) throws Exception {
        return null;
    }

    @Override
    public int addEmailContact(EmailContact emailContact) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO EmailContact (UserId, EmailId) values(:userId, :emailId)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(emailContact), holder);
        if(n > 0 && holder.getKeys().size() > 0) {
//            return holder.getKey().intValue();
            return (int)holder.getKeys().get("EmailContactId");
        }
        return 0;
    }

    @Override
    public boolean updateEmailContact(int emailContactId, EmailContact emailContact) throws Exception {
        String sql = "UPDATE EmailContact SET UserId = :userId, EmailId = :emailId WHERE EmailContactId = :emailContactId";
        emailContact.setEmailContactId(emailContactId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(emailContact)) > 0;
    }

    @Override
    public boolean updateEmailContactByUserId(int userId, EmailContact emailContact) throws Exception {
        String sql = "UPDATE EmailContact SET EmailId = :emailId WHERE UserId = :userId";
        emailContact.setUserId(userId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(emailContact)) > 0;
    }

    @Override
    public boolean deleteEmailContact(int emailContactId) throws Exception {
        String sql = "DELETE FROM EmailContact WHERE EmailContactId = :emailContactId";
        MapSqlParameterSource params = new MapSqlParameterSource("emailContactId", emailContactId);
        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean deleteEmailContactByUserId(int userId) throws Exception {
        String sql = "DELETE FROM EmailContact WHERE UserId = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public int getCountByEmailId(String emailId) throws Exception {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        MapSqlParameterSource params = new MapSqlParameterSource("emailId", emailId);
        jdbcTemplate.query("SELECT * FROM EmailContact WHERE EmailId= :emailId", params, countCallback);
        return countCallback.getRowCount();
    }
}
