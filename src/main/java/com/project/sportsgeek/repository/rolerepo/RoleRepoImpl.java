package com.project.sportsgeek.repository.rolerepo;

import com.project.sportsgeek.mapper.GenderRowMapper;
import com.project.sportsgeek.mapper.RoleRowMapper;
import com.project.sportsgeek.model.profile.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "roleRepo")
public class RoleRepoImpl implements RoleRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Role> findAllRole() {
        String sql = "SELECT * FROM Role";
        return jdbcTemplate.query(sql, new RoleRowMapper());
    }

    @Override
    public Role findRoleById(int roleId) throws Exception {
        String sql = "SELECT * FROM Role WHERE RoleId = :roleId";
        MapSqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
        List<Role> roleList = jdbcTemplate.query(sql, params, new RoleRowMapper());
        if(roleList.size() > 0){
            return roleList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int addRole(Role role) throws Exception {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Role (Name) values(:name)";
        int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(role), holder);
        if(n > 0) {
            return holder.getKey().intValue();
        }else {
            return 0;
        }
    }

    @Override
    public boolean updateRole(int roleId, Role role) throws Exception {
        String sql = "UPDATE Role SET Name = :name WHERE RoleId = :roleId";
        role.setRoleId(roleId);
        return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(role)) > 0;
    }

    @Override
    public boolean deleteRole(int roleId) throws Exception {
        String sql = "DELETE FROM Role WHERE RoleId = :roleId";
        MapSqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
        return jdbcTemplate.update(sql, params) > 0;
    }
}
