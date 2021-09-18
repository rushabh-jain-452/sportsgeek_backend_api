package com.project.sportsgeek.repository.userrepo;

import com.project.sportsgeek.model.profile.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "userRepo")
public interface UserRepository {

    List<UserResponse> findAllUsers() ;

    UserResponse findUserByUserId(int userId) throws Exception;

    UserWithPassword findUserWithPasswordByUserId(int userId) throws Exception;

    UserWithPassword findUserByUserName(String userName) throws Exception;

    List<UserResponse> findAllUsersByRole(int roleId) throws Exception;

    UserResponse findUserByEmailIdAndMobileNumber(User user) throws Exception;

    UserWinningAndLosingPoints findWinningAndLosingPointsByUserId(int userId) throws Exception;

    List<UserResponse> findUsersByStatus(boolean status) throws Exception;

    int addUser(UserWithPassword userWithPassword) throws Exception;

    boolean updateUser(int userId, User user) throws Exception;

    boolean updateUserWithProfilePicture(int userId, User user) throws Exception;

    boolean updateStatus(int userId, boolean status) throws Exception;

    boolean updateUserPassword(UserWithNewPassword userWithNewPassword) throws Exception;

    boolean updateForgetPassword(UserWithOtp userWithOtp) throws Exception;

    boolean updateUserRole(int userId, int roleId) throws Exception;

    boolean updateUserProfilePicture(int userId, String profilePicture) throws Exception;

    boolean updateUserAvailablePoints(int userId, int availablePoints) throws Exception;

    UserForLoginState authenticate(UserAtLogin userAtLogin) throws Exception;

    boolean deleteUser(int userId) throws Exception;

    boolean deleteUserProfilePicture(int userId) throws Exception;

    boolean addAvailablePoints(int userId, int points) throws Exception;

    boolean deductAvailablePoints(int userId, int points) throws Exception;

    int getUsersCountByUsername(String username) throws Exception;
}
