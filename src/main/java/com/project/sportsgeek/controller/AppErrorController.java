package com.project.sportsgeek.controller;

import com.project.sportsgeek.response.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(path = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppErrorController implements ErrorController {

    @GetMapping
    public ResponseEntity<Result<String>> onError(WebRequest webRequest){
//        return ResponseEntity.ok("<h1>SportsGeek Application</h1><br/><h3>Sorry, you are unauthorized to access.</h3>");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("<h1>SportsGeek Application</h1><br/><h3>Sorry, you are unauthorized to access.</h3>");
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("<h1>SportsGeek Application</h1><br/><h3>Sorry, you are unauthorized to access.</h3>");
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sorry, you are unauthorized to access.");
//        ResponseEntity(new Result<String>(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"));
        return new ResponseEntity(new Result<>(401, "Unauthorized"), HttpStatus.UNAUTHORIZED);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}