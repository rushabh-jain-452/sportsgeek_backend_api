package com.project.sportsgeek.repository.publicchatrepo;

import com.project.sportsgeek.mapper.PublicChatFormattedRowMapper;
import com.project.sportsgeek.mapper.PublicChatWithUserRowMapper;
import com.project.sportsgeek.mapper.VenueRowMapper;
import com.project.sportsgeek.model.PublicChat;
import com.project.sportsgeek.model.PublicChatFormatted;
import com.project.sportsgeek.model.PublicChatWithUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "publicChatRepo")
public class PublicChatRepoImpl implements PublicChatRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

//    @Override
//    public List<PublicChatWithUser> findAllPublicChat() {
//        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE ORDER BY ChatTimestamp";
//        return jdbcTemplate.query(sql, new PublicChatWithUserRowMapper());
//    }

    @Override
    public List<PublicChatWithUser> findAllPublicChatForLastDays(int days) {
//        MySQL
//        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND ChatTimestamp > DATE_SUB(CURRENT_TIMESTAMP, INTERVAL :days DAY) ORDER BY ChatTimestamp";
//        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND ChatTimestamp > DATE_SUB(CURRENT_TIMESTAMP, INTERVAL :days DAY) ORDER BY PublicChatId";
//        PostgreSQL
//        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND ChatTimestamp > (SELECT NOW() - INTERVAL ':days days') ORDER BY PublicChatId";
        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND ChatTimestamp > (SELECT NOW() - INTERVAL '" + days + " days') ORDER BY PublicChatId";
        MapSqlParameterSource params = new MapSqlParameterSource("days", days);
        return jdbcTemplate.query(sql, params, new PublicChatWithUserRowMapper());
    }

    @Override
    public List<PublicChatWithUser> findAllPublicChatAfterId(int publicChatId) {
//        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND PublicChatId > :publicChatId ORDER BY ChatTimestamp";
        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND PublicChatId > :publicChatId ORDER BY PublicChatId";
        MapSqlParameterSource params = new MapSqlParameterSource("publicChatId", publicChatId);
        return jdbcTemplate.query(sql, params, new PublicChatWithUserRowMapper());
    }

//    @Override
//    public List<PublicChatFormatted> findAllPublicChatFormatted() {
//        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE ORDER BY ChatTimestamp DESC";
//        return jdbcTemplate.query(sql, new PublicChatFormattedRowMapper());
//    }

    @Override
    public List<PublicChatFormatted> findAllPublicChatFormattedForLastDays(int days) {
//        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND ChatTimestamp > DATE_SUB(CURRENT_TIMESTAMP, INTERVAL :days DAY) ORDER BY ChatTimestamp DESC";
//        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND ChatTimestamp > DATE_SUB(CURRENT_TIMESTAMP, INTERVAL :days DAY) ORDER BY PublicChatId DESC";
//        PostgreSQL
        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND ChatTimestamp > (SELECT NOW() - INTERVAL '" + days + " days') ORDER BY PublicChatId DESC";
        MapSqlParameterSource params = new MapSqlParameterSource("days", days);
        return jdbcTemplate.query(sql, params, new PublicChatFormattedRowMapper());
    }

    @Override
    public List<PublicChatFormatted> findAllPublicChatFormattedAfterId(int publicChatId) {
//        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND PublicChatId > :publicChatId ORDER BY ChatTimestamp DESC";
        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE pc.Status=TRUE AND PublicChatId > :publicChatId ORDER BY PublicChatId DESC";
        MapSqlParameterSource params = new MapSqlParameterSource("publicChatId", publicChatId);
        return jdbcTemplate.query(sql, params, new PublicChatFormattedRowMapper());
    }

    @Override
    public List<PublicChatWithUser> findAllTodayPublicChat() {
        return null;
    }

    @Override
    public PublicChatWithUser findPublicChatById(int publicChatId) throws Exception {
        String sql = "SELECT PublicChatId, pc.UserId as UserId, FirstName, LastName, ProfilePicture, Message, pc.Status as Status, ChatTimestamp FROM PublicChat as pc INNER JOIN Users as u on pc.UserId=u.UserId WHERE PublicChatId = :publicChatId";
        MapSqlParameterSource params = new MapSqlParameterSource("publicChatId", publicChatId);
        List<PublicChatWithUser> publicChatList = jdbcTemplate.query(sql, params, new PublicChatWithUserRowMapper());
        if(publicChatList.size() > 0){
            return publicChatList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int addPublicChat(PublicChat publicChat) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO PublicChat (userId, message) VALUES (:userId, :message)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(publicChat), holder);
        if(n > 0 && holder.getKeys().size() > 0) {
//            return holder.getKey().intValue();
            return (int)holder.getKeys().get("PublicChatId");
        }
        return 0;
    }

    @Override
    public boolean updatePublicChat(int publicChatId, PublicChat publicChat) throws Exception {
        String sql = "UPDATE PublicChat SET UserId = :userId, Message=:message WHERE PublicChatId = :publicChatId";
        publicChat.setPublicChatId(publicChatId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(publicChat)) > 0;
    }

    @Override
    public boolean updatePublicChatStatus(int publicChatId, boolean status) throws Exception {
        String sql = "UPDATE PublicChat SET Status = :status WHERE PublicChatId = :publicChatId";
        MapSqlParameterSource params = new MapSqlParameterSource("publicChatId", publicChatId);
        params.addValue("status", status);
        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean deletePublicChat(int publicChatId) throws Exception {
        String sql = "DELETE FROM PublicChat WHERE PublicChatId = :publicChatId";
        MapSqlParameterSource params = new MapSqlParameterSource("publicChatId", publicChatId);
        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean deletePublicChatByUserId(int userId) throws Exception {
        String sql = "DELETE FROM PublicChat WHERE UserId = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbcTemplate.update(sql, params) > 0;
    }

}