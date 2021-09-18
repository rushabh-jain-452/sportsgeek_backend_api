package com.project.sportsgeek.service;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.model.Email;
import com.project.sportsgeek.model.EmailContact;
import com.project.sportsgeek.model.MobileContact;
import com.project.sportsgeek.model.profile.*;
import com.project.sportsgeek.repository.contestlogrepo.ContestLogRepository;
import com.project.sportsgeek.repository.contestrepo.ContestRepository;
import com.project.sportsgeek.repository.emailcontactrepo.EmailContactRepository;
import com.project.sportsgeek.repository.mobilecontactrepo.MobileContactRepository;
import com.project.sportsgeek.repository.publicchatrepo.PublicChatRepository;
import com.project.sportsgeek.repository.rechargerepo.RechargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.repository.userrepo.UserRepository;
import com.project.sportsgeek.response.Result;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	@Qualifier(value = "userRepo")
	UserRepository userRepository;

	@Autowired
	@Qualifier(value = "emailContactRepo")
	EmailContactRepository emailContactRepository;

	@Autowired
	@Qualifier(value = "mobileContactRepo")
	MobileContactRepository mobileContactRepository;

	@Autowired
	@Qualifier(value = "rechargeRepo")
	RechargeRepository rechargeRepository;

	@Autowired
	@Qualifier(value = "contestRepo")
	ContestRepository contestRepository;

	@Autowired
	@Qualifier(value = "contestLogRepo")
	ContestLogRepository contestLogRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	PublicChatRepository publicChatRepository;

	@Autowired
	ImageUploadService imageUploadService;

	private int otp;
	private int sendOtp;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- SELECT SERVICE ----------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	public Result<List<UserResponse>> findAllUsers() {
		List<UserResponse> userList = userRepository.findAllUsers();
		return new Result<>(200, userList);
	}

	public Result<UserResponse> findUserByUserId(int userId) throws Exception {
//		System.out.println("Service userId : " + userId);
		UserResponse user = userRepository.findUserByUserId(userId);
//		System.out.println("Service : " + user);
		if (user != null) {
			return new Result<>(200, user);
		}
		throw new ResultException(new Result<>(404, "User with userId: " + userId + " not found."));
	}

	public Result<UserWinningAndLosingPoints> findUserWinningAndLosingPoints(int userId) throws Exception {
		UserWinningAndLosingPoints userWinningAndLosingPoints = userRepository.findWinningAndLosingPointsByUserId(userId);
		if (userWinningAndLosingPoints != null) {
			return new Result<>(200, userWinningAndLosingPoints);
		}
		throw new ResultException(new Result<>(404, "User with userId: " + userId + " not found."));
	}

	public Result<UserWithPassword> findUserByUserName(String username) throws Exception {
		UserWithPassword userWithPassword = userRepository.findUserByUserName(username);
		if (userWithPassword != null) {
			return new Result<>(200, userWithPassword);
		}
		throw new ResultException(new Result<>(404, "User with Username: " + username + " not found."));
	}

	public Result<List<UserResponse>> findUsersByStatus(boolean status) throws Exception {
		List<UserResponse> userList = userRepository.findUsersByStatus(status);
		return new Result<>(200, userList);
	}

	public Result<List<UserResponse>> findUsersByRole(int roleId) throws Exception {
		List<UserResponse> userList = userRepository.findAllUsersByRole(roleId);
		return new Result<>(200, userList);
	}

	@SneakyThrows
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserWithPassword userWithPassword = userRepository.findUserByUserName(username);
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + userWithPassword.getRole()));
		if (userWithPassword == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		} else {
			return new org.springframework.security.core.userdetails.User(userWithPassword.getUsername(),
					userWithPassword.getPassword(), authorities);
		}
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- AUTHENTICATION SERVICE --------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	public Result<UserForLoginState> authenticate(UserAtLogin userAtLogin) throws Exception {
		UserForLoginState userForLoginState = userRepository.authenticate(userAtLogin);
//		System.out.println("UserForLoginState:" + userForLoginState);
		if (userForLoginState != null) {
			if (userForLoginState.isStatus() == false) {
//				return new Result<>(401, "Sorry! you have been blocked by the admin");
//				throw new DisabledException("Sorry! you have been blocked by the admin");
//				throw new ResultException(new Result<>(401, "Sorry! you have been blocked by the admin",
//						new ArrayList<>(Arrays.asList(new Result.SportsGeekSystemError(999,
//								"Sorry! you have been blocked by the admin")))));
				throw new ResultException(new Result<>(401, "Sorry! you have been blocked by the admin."));
			} else {
				return new Result<>(200, userForLoginState);
			}
		}
//		return new Result<>(400, "Invalid username or password!");
//		throw new BadCredentialsException("Invalid username or password!");
		throw new ResultException(new Result<>(400, "Invalid username or password!"));
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- INSERT SERVICE ----------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	public Result<User> addUser(UserWithPassword userWithPassword, MultipartFile multipartFile) throws Exception {
		int usernameCount = userRepository.getUsersCountByUsername(userWithPassword.getUsername());
		int emailCount = emailContactRepository.getCountByEmailId(userWithPassword.getEmail());
		if (usernameCount > 0) {
			throw new ResultException(new Result<>(404, "Username already exists."));
		}
		else if(emailCount > 0){
			throw new ResultException(new Result<>(400, "Email already exists."));
		}
		else {
			if(userWithPassword.getRoleId() == 0){
				userWithPassword.setRoleId(2);
			}
			// Upload Image
//			if(multipartFile.getOriginalFilename().length() != 0){
			if(multipartFile != null){
				File file = imageUploadService.uploadImage(multipartFile);
				if (file != null) {
					userWithPassword.setProfilePicture(file.getName());
				}
				else
				{
					throw new ResultException(new Result<>(400, "Unable to upload Profile Picture."));
				}
			}else{
				userWithPassword.setProfilePicture("");
			}
			// Add User
			int userId = userRepository.addUser(userWithPassword);
			userWithPassword.setUserId(userId);
			userWithPassword.setProfilePicture(Config.FIREBASE_URL + userWithPassword.getProfilePicture() + Config.FIREBASE_PARAMS);
			if (userId > 0) {
				// Add Email
				EmailContact emailContact = new EmailContact();
				emailContact.setUserId(userId);
				emailContact.setEmailId(userWithPassword.getEmail());
				emailContactRepository.addEmailContact(emailContact);
				// Add Mobile Number
				MobileContact mobileContact = new MobileContact();
				mobileContact.setUserId(userId);
				mobileContact.setMobileNumber(userWithPassword.getMobileNumber());
				mobileContactRepository.addMobileContact(mobileContact);

				// Send Email to User
				String subject = "SportsGeek Registration Successful!!";
				String msg = "Hello " + userWithPassword.getFirstName() + " " + userWithPassword.getLastName() +
						"\n\nYou have successfully registered on SportsGeek." +
						"\n\nYour account is pending for approval by the Admin. Please wait until admin approves your account." +
						"\n\nThanking you\nSportsGeek Team";
				Email email = Email.builder().setSubject(subject).setTo(userWithPassword.getEmail()).message(msg)
						.build();
				emailService.sendEmail(email);

				// Send Email to Admin
				String admin_subject = "New User Registration Approval!!";
				String adm_msg = "Hello Admin\n\nA new user has registered on SportsGeek with the following details :\n" +
						"\nName : " + userWithPassword.getFirstName() + " " + userWithPassword.getLastName() +
						"\nEmail : " + userWithPassword.getEmail() +
						"\nMobile Number : " + userWithPassword.getMobileNumber() +
						"\nGender : " + (userWithPassword.getGenderId() == 1 ? "Male" : "Female") +
						"\nUsername : " + userWithPassword.getUsername() +
						"\n\nPlease Approve if " + (userWithPassword.getGenderId() == 1 ? "he" : "she") + " is a valid user." +
						"\n\nThanking you\n" + "SportsGeek Team";
				String adminEmailId = "admn.sportsgeek@gmail.com";
				Email adminEmail = Email.builder().setSubject(admin_subject).setTo(adminEmailId).message(adm_msg)
						.build();
				emailService.sendEmail(adminEmail);
				return new Result<>(201, userWithPassword);
			} else {
				throw new ResultException(new Result<>(500, "Unable to add User"));
			}
		}
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- UPDATE SERVICE ----------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	public Result<User> updateUser(int userId, User user, MultipartFile multipartFile, boolean updateProfilePicture) throws Exception {
		boolean result = false;
		// Upload Image
//		if(multipartFile.getOriginalFilename().length() != 0){
		if(updateProfilePicture == true){
			if(multipartFile != null){
				File file = imageUploadService.uploadImage(multipartFile);
				if (file != null) {
					user.setProfilePicture(file.getName());
				}
				else
				{
					throw new ResultException(new Result<>(400, "Unable to upload Profile Picture."));
				}
			}
			else{  // If image not passed
				user.setProfilePicture("");
			}
			result = userRepository.updateUserWithProfilePicture(userId, user);
		}
		else{
			result = userRepository.updateUser(userId, user);
		}
		// Update User
		if (result == true) {
			// Update Email
			EmailContact emailContact = new EmailContact();
			emailContact.setEmailId(user.getEmail());
			emailContactRepository.updateEmailContactByUserId(userId, emailContact);
			// Add Mobile Number
			MobileContact mobileContact = new MobileContact();
			mobileContact.setMobileNumber(user.getMobileNumber());
			mobileContactRepository.updateMobileContactByUserId(userId, mobileContact);
			// Set Full URL of Profile Picture
			user.setProfilePicture(Config.FIREBASE_URL + user.getProfilePicture() + Config.FIREBASE_PARAMS);
			return new Result<>(200, user);
		}
		throw new ResultException(new Result<>(404, "User with userId: " + userId + " not found."));
	}

	public Result<String> updateStatus(int userId, boolean status) throws Exception {
		if (userRepository.updateStatus(userId, status)) {
			UserResponse user = userRepository.findUserByUserId(userId);
			String sub = "Account Approved!!";
			String msg = "Congratulations " + user.getFirstName() + " " + user.getLastName() + ",\n\n" +
					"Your account is approved by the Admin.\n\n" +
					"Your username is \"" + user.getUsername() + "\".\n" +
					"Now, You can login to the app and enjoy SportsGeek.\n\n" +
					"Thanking you\nSportsGeek Team";
			String userEmailId = user.getEmail();
			Email email = Email.builder().setSubject(sub).setTo(userEmailId).message(msg).build();
			emailService.sendEmail(email);
			return new Result<>(200, "status of given id(" + userId + ") has been successfully updated");
		}
		throw new ResultException(new Result<>(404, "User with userId: " + userId + " not found."));
	}

	public Result<String> updateUserRole(int userId, int roleId) throws Exception {
		if (userRepository.updateUserRole(userId, roleId)) {
			return new Result<>(200, "Successfully Assigned Role to User");
		}
		throw new ResultException(new Result<>(500, "Internal Server error!, Unable to update the Role"));
	}

//	------------------------------------------------- UPDATE PASSWORD SERVICE --------------------------------------------------------------------

	public Result<String> updatePassword(UserWithNewPassword userWithNewPassword) throws Exception {
		UserWithPassword userWithOldPassword = userRepository.findUserWithPasswordByUserId(userWithNewPassword.getUserId());
		System.out.println(userWithOldPassword);
		if (userWithOldPassword != null && bCryptPasswordEncoder.matches(userWithNewPassword.getOldPassword(), userWithOldPassword.getPassword())) {
			userWithNewPassword.setNewPassword(bCryptPasswordEncoder.encode(userWithNewPassword.getNewPassword()));
			if (userRepository.updateUserPassword(userWithNewPassword)) {
				return new Result<>(200, "Password has been successfully updated");
			} else {
				throw new ResultException(new Result<>(500, "Internal Server error!, Unable to update the password"));
			}
		}
		throw new ResultException(new Result<>(400, "Old Password does not match!"));
	}

//	------------------------------------------------- UPDATE FORGOT PASSWORD SERVICE -------------------------------------------------------------

	public int generateOTP() {
		Random random = new Random();
		otp = 100000 + random.nextInt(900000);
		return otp;
	}

	public Result<UserResponse> findUserByEmailIdAndMobileNumber(User user) throws Exception {
		UserResponse foundUser = userRepository.findUserByEmailIdAndMobileNumber(user);
		System.out.println(foundUser);
		if (foundUser != null) {
			sendOtp = generateOTP();
			String subject = "Forget Password OTP Verification!!";
			String msg = "Hello " + foundUser.getFirstName() + " " + foundUser.getLastName() +
					"\n\nYour OTP For Password Change.\n\n" +
					"OTP : " + sendOtp +
					"\n\nThanking you\nSportsGeek Team";
			System.out.println(msg);
			Email email = Email.builder().setSubject(subject).setTo(foundUser.getEmail()).message(msg).build();
			emailService.sendEmail(email);
			return new Result<>(200, foundUser);
		} else {
			throw new ResultException(new Result<>(404, "Email Id or Mobile Number Not Found"));
		}
	}

	public Result<String> updateForgetPassword(UserWithOtp userWithOtp) throws Exception {
//		System.out.println("Send OTP in Update Password Service:" + sendOtp);
//		System.out.println("Otp Received by service" + userWithOtp.getOtp());
		if (userWithOtp.getOtp() == sendOtp) {
			sendOtp = 0;
			if (userRepository.updateForgetPassword(userWithOtp)) {
				return new Result<>(200, "password has been successfully Changed");
			} else {
				throw new ResultException(new Result<>(500, "Internal Server error!, Unable to update the password"));
			}
		} else {
			throw new ResultException(new Result<>(404, "OTP Not Match"));
		}
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- DELETE SERVICE ----------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	public Result<String> deleteUser(int userId) throws Exception {
		System.out.println("UserId"+userId);
		UserResponse user = userRepository.findUserByUserId(userId);
		if (user != null) {
			emailContactRepository.deleteEmailContactByUserId(userId);
			mobileContactRepository.deleteMobileContactByUserId(userId);
			rechargeRepository.deleteRechargeByUserId(userId);
			contestRepository.deleteContestsByUserId(userId);
			contestLogRepository.deleteContestLogByUserId(userId);
			publicChatRepository.deletePublicChatByUserId(userId);
			userRepository.deleteUser(userId);
			return new Result<>(200, "User deleted Successfully!!");
		}
		throw new ResultException(new Result<>(404, "User with userId: " + userId + " not found."));
	}

	public Result<String> deleteUserProfilePicture(int userId) throws Exception {
		if (userRepository.deleteUserProfilePicture(userId)) {
			return new Result<>(200, "User Profile Picture deleted Successfully!!");
		}
		throw new ResultException(new Result<>(404, "User with userId: " + userId + " not found."));
	}

}
