package com.project.sportsgeek.repository.userrepo;

import java.util.List;
import java.util.Map;

import com.project.sportsgeek.mapper.*;
import com.project.sportsgeek.model.profile.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository(value = "userRepo")
public class UserRepoImpl implements UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- SELECT QUERY ------------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------


	@Override
	public List<UserResponse> findAllUsers() {
//		String sql = "SELECT * FROM User";
		String sql = "SELECT User.UserId as UserId, FirstName, LastName, User.GenderId, Gender.Name as GenderName, User.RoleId, Role.Name as RoleName, Username, AvailablePoints, ProfilePicture, Status, EmailContact.EmailId as Email, MobileContact.MobileNumber as MobileNumber FROM User inner join EmailContact on User.UserId=EmailContact.UserId inner join Gender on User.GenderId=Gender.GenderId inner join Role on User.RoleId=Role.RoleId inner join MobileContact on User.UserId=MobileContact.UserId ORDER BY User.UserId";
		return jdbcTemplate.query(sql, new UserResponseRowMapper());
	}

	@Override
	public UserResponse findUserByUserId(int userId) throws Exception {
//		System.out.println("Repo userId : " + userId);
		String sql = "SELECT User.UserId as UserId, FirstName, LastName, User.GenderId, Gender.Name as GenderName, User.RoleId, Role.Name as RoleName, Username, AvailablePoints, ProfilePicture, Status, EmailContact.EmailId as Email, MobileContact.MobileNumber as MobileNumber FROM User inner join EmailContact on User.UserId=EmailContact.UserId inner join Gender on User.GenderId=Gender.GenderId inner join Role on User.RoleId=Role.RoleId inner join MobileContact on User.UserId=MobileContact.UserId WHERE User.UserId = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
		List<UserResponse> userList = jdbcTemplate.query(sql, params, new UserResponseRowMapper());
//		System.out.println("userList Size : " + userList.size());
//		System.out.println("userList : " + userList);
		if(userList.size() > 0){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public UserWithPassword findUserWithPasswordByUserId(int userId) throws Exception {
		String sql = "SELECT Username, Password, r.Name as Role FROM User as u INNER JOIN Role as r on u.RoleId=r.RoleId WHERE UserId= :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
		List<UserWithPassword> userList = jdbcTemplate.query(sql, params, new UserWithPasswordRowMapper());
		if(userList.size() > 0){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public List<UserResponse> findAllUsersByRole(int roleId) throws Exception {
		String sql = "SELECT User.UserId as UserId, FirstName, LastName, User.GenderId, Gender.Name as GenderName, User.RoleId, Role.Name as RoleName, Username, AvailablePoints, ProfilePicture, Status, EmailContact.EmailId as Email, MobileContact.MobileNumber as MobileNumber FROM User inner join EmailContact on User.UserId=EmailContact.UserId inner join Gender on User.GenderId=Gender.GenderId inner join Role on User.RoleId=Role.RoleId inner join MobileContact on User.UserId=MobileContact.UserId WHERE RoleId= :roleId ORDER BY User.UserId";
		MapSqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
		return jdbcTemplate.query(sql, params, new UserResponseRowMapper());
	}

	@Override
	public UserResponse findUserByEmailIdAndMobileNumber(User user) throws Exception {
		String sql = "SELECT User.UserId as UserId, FirstName, LastName, User.GenderId, Gender.Name as GenderName, User.RoleId, Role.Name as RoleName, Username, AvailablePoints, ProfilePicture, Status, EmailContact.EmailId as Email, MobileContact.MobileNumber as MobileNumber FROM User inner join EmailContact on User.UserId=EmailContact.UserId inner join Gender on User.GenderId=Gender.GenderId inner join Role on User.RoleId=Role.RoleId inner join MobileContact on User.UserId=MobileContact.UserId WHERE EmailContact.EmailId = :emailId AND MobileContact.MobileNumber = :mobileNumber";
		MapSqlParameterSource params = new MapSqlParameterSource("emailId", user.getEmail());
		params.addValue("mobileNumber", user.getMobileNumber());
		List<UserResponse> userList = jdbcTemplate.query(sql, params, new UserResponseRowMapper());
		if(userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<UserResponse> findUsersByStatus(boolean status) throws Exception {
		String sql = "SELECT User.UserId as UserId, FirstName, LastName, User.GenderId, Gender.Name as GenderName, User.RoleId, Role.Name as RoleName, Username, AvailablePoints, ProfilePicture, Status, EmailContact.EmailId as Email, MobileContact.MobileNumber as MobileNumber FROM User inner join EmailContact on User.UserId=EmailContact.UserId inner join Gender on User.GenderId=Gender.GenderId inner join Role on User.RoleId=Role.RoleId inner join MobileContact on User.UserId=MobileContact.UserId WHERE User.Status = :status ORDER BY User.UserId";
		MapSqlParameterSource params = new MapSqlParameterSource("status", status);
		return jdbcTemplate.query(sql, params, new UserResponseRowMapper());
	}

	@Override
	public UserWinningAndLosingPoints findWinningAndLosingPointsByUserId(int userId) throws Exception {
		if(findUserByUserId(userId) != null){
			UserWinningAndLosingPoints userWinningAndLosingPoints = new UserWinningAndLosingPoints();
			userWinningAndLosingPoints.setUserId(userId);
			// Winning Points
			try{
				String sql = "select sum(WinningPoints) as WinningPoints from Contest where UserId = :userId";
				MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
				int winningPoints = jdbcTemplate.queryForObject(sql, params, Integer.class);
				userWinningAndLosingPoints.setWinningPoints(winningPoints);
			}catch(Exception ex){
				userWinningAndLosingPoints.setWinningPoints(0);
			}
			// Losing Points
			try{
				String sql = "SELECT SUM(ContestPoints) as LoosingPoints FROM Contest as c INNER JOIN Matches as m ON c.MatchId = m.MatchId WHERE WinningPoints=0 AND m.ResultStatus=1 AND UserId = :userId";
				MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
				int losingPoints = jdbcTemplate.queryForObject(sql, params, Integer.class);
				userWinningAndLosingPoints.setLosingPoints(losingPoints);
			}catch(Exception ex){
				userWinningAndLosingPoints.setLosingPoints(0);
			}
			// No of Winning Matches
			try{
				String sql = "SELECT COUNT(*) FROM Contest WHERE UserId=:userId AND WinningPoints > ContestPoints ORDER BY MatchId;";
				MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
				int n = jdbcTemplate.queryForObject(sql, params, Integer.class);
				userWinningAndLosingPoints.setNumberOfWinningMatches(n);
			}catch(Exception ex){
				userWinningAndLosingPoints.setNumberOfWinningMatches(0);
			}
			// No of Losing Matches
			try{
				String sql = "SELECT COUNT(*) FROM Contest WHERE UserId=:userId AND WinningPoints = 0 ORDER BY MatchId;";
				MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
				int n = jdbcTemplate.queryForObject(sql, params, Integer.class);
				userWinningAndLosingPoints.setNumberOfLosingMatches(n);
			}catch(Exception ex){
				userWinningAndLosingPoints.setNumberOfLosingMatches(0);
			}
			return userWinningAndLosingPoints;
		}else{
			return null;
		}
	}

	@Override
	public UserWithPassword findUserByUserName(String username) throws Exception {
		String sql = "SELECT u.UserName as UserName,u.Password as Password,r.Name as Role FROM User as u INNER JOIN Role as r on u.RoleId=r.RoleId WHERE UserName = :username";
		MapSqlParameterSource params = new MapSqlParameterSource("username", username);
		List<UserWithPassword> userList = jdbcTemplate.query(sql, params, new UserWithPasswordRowMapper());
		if(userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- AUTHENTICATION QUERY ----------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------


	@Override
	public UserForLoginState authenticate(UserAtLogin userAtLogin) throws Exception {
//		String sql = "select u.UserId, u.UserName, r.Name, u.Status from User as u inner join Role as r on u.RoleId = r.RoleId where u.UserName=:username";
		String sql = "SELECT u.UserId AS UserId, Username, r.Name AS Role, Status from User as u inner join Role as r on u.RoleId = r.RoleId where Username=:username";
//		String sql = "SELECT u.UserId AS UserId, Username, r.Name AS Role, Status from User as u inner join Role as r on u.RoleId = r.RoleId where u.UserName=:username and u.Password=:password";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new BeanPropertySqlParameterSource(userAtLogin));
//		System.out.println("List size : " + list.size());
		if (list.size() > 0) {
			return new UserForLoginState(Integer.parseInt(list.get(0).get("UserId") + ""),
					list.get(0).get("UserName") + "", list.get(0).get("Role") + "",
					Boolean.parseBoolean(list.get(0).get("Status").toString()), "");
		}
		return null;
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- INSERT QUERY -----------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public int addUser(UserWithPassword userWithPassword) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		String sql = "INSERT INTO User (FirstName,LastName,GenderId,Username,Password,ProfilePicture,RoleId,AvailablePoints,Status)"
				+ "values(:firstName,:lastName,:genderId,:Username,:password,:profilePicture,:roleId,:availablePoints,:status)";
		int n = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(userWithPassword), holder);
		if(n > 0){
			return holder.getKey().intValue();
		}else{
			return 0;
		}
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- UPDATE QUERY -----------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public boolean updateUser(int userId, User user) throws Exception {
//		String sql = "UPDATE User SET FirstName = :firstName, LastName = :lastName, GenderId = :genderId, Username = :username WHERE UserId=:userId";
		String sql = "UPDATE User SET FirstName = :firstName, LastName = :lastName, GenderId = :genderId WHERE UserId=:userId";
		user.setUserId(userId);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user)) > 0;
	}

	@Override
	public boolean updateUserWithProfilePicture(int userId, User user) throws Exception {
//		String sql = "UPDATE User SET FirstName = :firstName, LastName = :lastName, GenderId = :genderId, Username = :username, ProfilePicture = :profilePicture WHERE UserId=:userId";
		String sql = "UPDATE User SET FirstName = :firstName, LastName = :lastName, GenderId = :genderId, ProfilePicture = :profilePicture WHERE UserId=:userId";
		user.setUserId(userId);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user)) > 0;
	}

	@Override
	public boolean updateStatus(int userId, boolean status) throws Exception {
		String sql = "UPDATE User set Status = :status WHERE UserId = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("status", status);
		params.addValue("userId", userId);
		return jdbcTemplate.update(sql, params) > 0;
	}

	@Override
	public boolean updateUserPassword(UserWithNewPassword userWithNewPassword) throws Exception {
		String sql = "UPDATE User SET Password = :newPassword WHERE UserId = :userId";
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(userWithNewPassword)) > 0;
	}

	@Override
	public boolean updateForgetPassword(UserWithOtp userWithOtp) throws Exception {
		System.out.println("Password : " + userWithOtp.getPassword());
		String encodedPassword = bCryptPasswordEncoder.encode(userWithOtp.getPassword());
		String sql = "UPDATE User SET Password = :encodedPassword WHERE UserId = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("encodedPassword", encodedPassword);
		params.addValue("userId", userWithOtp.getUserId());
		return jdbcTemplate.update(sql, params) > 0;
	}

	@Override
	public boolean updateUserRole(int userId, int roleId) throws Exception {
		String sql = "UPDATE User SET RoleId = :roleId WHERE UserId = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
		params.addValue("userId", userId);
		return jdbcTemplate.update(sql, params) > 0;
	}

	@Override
	public boolean updateUserProfilePicture(int userId, String profilePicture) throws Exception {
		String sql = "UPDATE User SET ProfilePicture = :profilePicture WHERE UserId = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("profilePicture", profilePicture);
		params.addValue("userId", userId);
		return jdbcTemplate.update(sql, params) > 0;
	}

	@Override
	public boolean updateUserAvailablePoints(int userId, int availablePoints) throws Exception {
		String sql = "UPDATE User SET AvailablePoints = :availablePoints WHERE UserId =:userId";
		MapSqlParameterSource params = new MapSqlParameterSource("availablePoints", availablePoints);
		return jdbcTemplate.update(sql, params) > 0;
	}

	@Override
	public boolean addAvailablePoints(int userId, int points) throws Exception {
		String sql = "UPDATE User SET AvailablePoints = AvailablePoints + :points WHERE UserId = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("points", points);
		params.addValue("userId", userId);
		return jdbcTemplate.update(sql, params) > 0;
	}

	@Override
	public boolean deductAvailablePoints(int userId, int points) throws Exception {
		String sql = "UPDATE User SET AvailablePoints = AvailablePoints-:points WHERE UserId = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("points", points);
		params.addValue("userId", userId);
		return jdbcTemplate.update(sql, params) > 0;
	}

	@Override
	public int getUsersCountByUsername(String username) throws Exception {
		RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
		MapSqlParameterSource params = new MapSqlParameterSource("username", username);
		jdbcTemplate.query("SELECT * FROM User WHERE Username= :username", params, countCallback);
		return countCallback.getRowCount();
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- DELETE QUERY -----------------------------------------------------------------------------
//	--------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public boolean deleteUser(int userId) throws Exception {
		String sql = "DELETE FROM User WHERE UserId = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
		return jdbcTemplate.update(sql, params) > 0;
	}

	@Override
	public boolean deleteUserProfilePicture(int userId) throws Exception {
		String sql = "UPDATE User SET ProfilePicture='' WHERE UserId = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
		return jdbcTemplate.update(sql, params) > 0;
	}

}
