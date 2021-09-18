package com.project.sportsgeek.controller;

import com.project.sportsgeek.model.Gender;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.GenderService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/genders", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenderController {

    @Autowired
    GenderService genderService;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    @GetMapping
    public ResponseEntity<List<Gender>> getAllGenders() {
        Result<List<Gender>> genderList = genderService.findAllGender();
        return new ResponseEntity<>(genderList.getData(), HttpStatus.valueOf(genderList.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping("/{genderId}")
    public ResponseEntity<Gender> getGenderById(@PathVariable int genderId) throws Exception {
        Result<Gender> genderList = genderService.findGenderById(genderId);
        return new ResponseEntity<>(genderList.getData(), HttpStatus.valueOf(genderList.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasAnyRole('Admin','User')")
    @PostMapping
    public ResponseEntity<Gender> addGender(@RequestBody(required = true) Gender gender) throws Exception {
        Result<Gender> genderResult = genderService.addGender(gender);
        return new ResponseEntity(genderResult.getData(), HttpStatus.valueOf(genderResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 400, message = "Missing or invalid request body"), @ApiResponse(code = 500, message = "Internal error")})
    @PreAuthorize("hasAnyRole('Admin','User')")
    @PutMapping("/{genderId}")
    public ResponseEntity<Gender> updateGender(@PathVariable int genderId, @RequestBody(required = true) Gender gender) throws Exception {
        Result<Gender> genderResult = genderService.updateGender(genderId, gender);
        return new ResponseEntity(genderResult.getData(), HttpStatus.valueOf(genderResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 409, message = "Schema is in use"), @ApiResponse(code = 500, message = "Error deleting schema")})
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{genderId}")
    public ResponseEntity<Result<String>> deleteGenderById(@PathVariable int genderId) throws Exception {
        Result<String> result = genderService.deleteGender(genderId);
//        return new ResponseEntity<>(new ResponseMessage(result.getMessage()), HttpStatus.valueOf(result.getCode()));
        return new ResponseEntity(result, HttpStatus.valueOf(result.getCode()));
    }
}
