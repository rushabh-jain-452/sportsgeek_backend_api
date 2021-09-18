package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.jwtconfig.JwtTokenUtil;
import com.project.sportsgeek.model.profile.*;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- SELECT CONTROLLER
//	-------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        Result<List<UserResponse>> userList = userService.findAllUsers();
        return new ResponseEntity<>(userList.getData(), HttpStatus.valueOf(userList.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int userId) throws Exception {
//        System.out.println("userId : " + userId);
        Result<UserResponse> userResult = userService.findUserByUserId(userId);
        return new ResponseEntity<>(userResult.getData(), HttpStatus.valueOf(userResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping("/user-name/{username}")
    public ResponseEntity<UserWithPassword> getUserByUserName(@PathVariable String username) throws Exception {
        Result<UserWithPassword> userResult = userService.findUserByUserName(username);
        return new ResponseEntity<>(userResult.getData(), HttpStatus.valueOf(userResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping("/{userId}/winning-losing-points")
    public ResponseEntity<UserWinningAndLosingPoints> getUserWinningAndLosingPoints(@PathVariable int userId)
            throws Exception {
        Result<UserWinningAndLosingPoints> userResult = userService.findUserWinningAndLosingPoints(userId);
        return new ResponseEntity<>(userResult.getData(), HttpStatus.valueOf(userResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping("/user-with-status/{status}")
    public ResponseEntity<List<UserResponse>> getUsersByStatus(@PathVariable boolean status) throws Exception {
        Result<List<UserResponse>> userResult = userService.findUsersByStatus(status);
        return new ResponseEntity<>(userResult.getData(), HttpStatus.valueOf(userResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasAnyRole('Admin')")
    @GetMapping("/role-id/{roleId}")
    public ResponseEntity<List<UserResponse>> getUserByRoleId(@PathVariable int roleId) throws Exception {
        Result<List<UserResponse>> userResult = userService.findUsersByRole(roleId);
        return new ResponseEntity<>(userResult.getData(), HttpStatus.valueOf(userResult.getCode()));
    }

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- INSERT CONTROLLER
//	--------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Bad Request")})
    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("genderId") int genderId, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("mobileNumber") String mobileNumber, @RequestParam("availablePoints") int availablePoints ,@RequestParam(value="profilePicture",required = false) MultipartFile multipartFile) throws Exception {
        UserWithPassword userWithPassword = new UserWithPassword();
        userWithPassword.setFirstName(firstName);
        userWithPassword.setLastName(lastName);
        userWithPassword.setGenderId(genderId);
        userWithPassword.setUsername(username);
        userWithPassword.setPassword(bCryptPasswordEncoder.encode(password));
        userWithPassword.setEmail(email);
        userWithPassword.setMobileNumber(mobileNumber);
        userWithPassword.setAvailablePoints(availablePoints);

        Result<User> userResult = userService.addUser(userWithPassword, multipartFile);
        return new ResponseEntity<>(userResult.getData(), HttpStatus.valueOf(userResult.getCode()));
    }

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- UPDATE CONTROLLER
//	--------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
    @PreAuthorize("hasAnyRole('Admin','User')")
    @PutMapping("/update-password")
    public ResponseEntity<Result<String>> updatePassword(@RequestBody(required = true) UserWithNewPassword userWithNewPassword) throws Exception {
//        System.out.println(userWithNewPassword);
        Result<String> userResult = userService.updatePassword(userWithNewPassword);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/{userId}/update-status/{status}")
    public ResponseEntity<Result<String>> updateStatus(@PathVariable int userId, @PathVariable boolean status) throws Exception {
        Result<String> userResult = userService.updateStatus(userId, status);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
    @PreAuthorize("hasAnyRole('Admin','User')")
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("genderId") int genderId, @RequestParam("email") String email, @RequestParam("mobileNumber") String mobileNumber, @RequestParam(value="profilePicture", required=false) MultipartFile multipartFile, @RequestParam(value="updateProfilePicture", required=false) Boolean updateProfilePicture) throws Exception {
//        String filename = multipartFile.getOriginalFilename();
//        System.out.println("Filename : '" + filename + "'");
//        System.out.println(multipartFile.getOriginalFilename().length());
        if(updateProfilePicture == null){
            if(multipartFile != null){
                updateProfilePicture = true;
            }else{
                updateProfilePicture = false;
            }
        }
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGenderId(genderId);
        user.setEmail(email);
        user.setMobileNumber(mobileNumber);

        Result<User> userResult = userService.updateUser(userId, user, multipartFile, updateProfilePicture);
        return new ResponseEntity<>(userResult.getData(), HttpStatus.valueOf(userResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/{userId}/update-role/{roleId}")
    public ResponseEntity<Result<String>> updateUserRole(@PathVariable int userId, @PathVariable int roleId)
            throws Exception {
        Result<String> userResult = userService.updateUserRole(userId, roleId);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

//	------------------------------------------------- FORGET PASSWORD CONTROLLER
//	-----------------------------------------------------------------

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
//    @PreAuthorize("hasAnyRole('Admin','User')")
    @PostMapping("/forget-password")
    public ResponseEntity<UserResponse> getUserByEmailIdAndMobileNumber(@RequestBody(required = true) User user) throws Exception {
        Result<UserResponse> userResult = userService.findUserByEmailIdAndMobileNumber(user);
        return new ResponseEntity<>(userResult.getData(), HttpStatus.valueOf(userResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal error")})
//    @PreAuthorize("hasAnyRole('Admin','User')")
    @PutMapping("/forget-password")
    public ResponseEntity<Result<String>> forgetPassword(@RequestBody(required = true) UserWithOtp userWithOtp)
            throws Exception {
        Result<String> userResult = userService.updateForgetPassword(userWithOtp);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- DELETE CONTROLLER
//	-------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 409, message = "Schema is in use"),
            @ApiResponse(code = 500, message = "Error deleting schema")})
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Result<String>> deleteUser(@PathVariable int userId) throws Exception {
        Result<String> userResult = userService.deleteUser(userId);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted schema"),
            @ApiResponse(code = 404, message = "Schema not found"),
            @ApiResponse(code = 409, message = "Schema is in use"),
            @ApiResponse(code = 500, message = "Error deleting schema")})
//    @PreAuthorize("hasAnyRole('Admin','User')")
    @DeleteMapping("/{userId}/remove-profile-picture")
    public ResponseEntity<Result<String>> deleteUserProfilePicture(@PathVariable int userId) throws Exception {
        Result<String> userResult = userService.deleteUserProfilePicture(userId);
        return new ResponseEntity<>(userResult, HttpStatus.valueOf(userResult.getCode()));
    }

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- AUTHENTICATION CONTROLLER
//	-----------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

    @PostMapping("/authenticate")
    public ResponseEntity<UserForLoginState> authenticateStatus(@RequestBody(required = true) UserAtLogin userAtLogin) throws Exception {
        Result<UserForLoginState> userResult = userService.authenticate(userAtLogin);
        // Generate token
        authenticate(userAtLogin.getUsername(), userAtLogin.getPassword());
//        System.out.println("Authentication Success.");
        final UserDetails userDetails = userService.loadUserByUsername(userAtLogin.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        userResult.getData().setToken(token);
        return new ResponseEntity<>(userResult.getData(), HttpStatus.valueOf(userResult.getCode()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
            throw new ResultException((new Result<>(401, "Sorry! you have been blocked by the admin.")));
        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
            throw new ResultException((new Result<>(400, "Invalid username or password!")));
        }
    }
}
