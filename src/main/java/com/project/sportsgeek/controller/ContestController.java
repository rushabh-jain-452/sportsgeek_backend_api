package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Contest;
import com.project.sportsgeek.model.ContestWithResult;
import com.project.sportsgeek.model.ContestWithUser;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.ContestService;
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
//@RequestMapping(path = "/contest",produces = MediaType.APPLICATION_JSON_VALUE)
public class ContestController {
    @Autowired
    ContestService contestService;

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Contest.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(path = "/contest/{matchId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContestWithUser>> getContestByMatchId(@PathVariable int matchId) throws Exception {
        Result<List<ContestWithUser>> contestList = contestService.findContestByMatchId(matchId);
        return new ResponseEntity<>(contestList.getData(), HttpStatus.valueOf(contestList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Contest.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/matches/{matchId}/contest",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContestWithResult>> getContestResultByMatchId(@PathVariable int matchId) throws Exception {
        Result<List<ContestWithResult>> contestList = contestService.findContestResultByMatchId(matchId);
        return new ResponseEntity<>(contestList.getData(), HttpStatus.valueOf(contestList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Contest.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/users/{userId}/contest/{matchId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contest> getContestByUserAndMatch(@PathVariable int userId , @PathVariable int matchId) throws Exception {
        Result<Contest> contestList = contestService.findContestByUserAndMatch(userId, matchId);
        return new ResponseEntity<>(contestList.getData(), HttpStatus.valueOf(contestList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Contest.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @PostMapping(path = "/contest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contest> addContest(@RequestBody(required = true) Contest contest) throws Exception {
        Result<Contest> contestResult = contestService.addContest(contest);
        return new ResponseEntity(contestResult.getData(), HttpStatus.valueOf(contestResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Contest.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @PutMapping(value = "/contest/{contestId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contest> updateContest(@PathVariable int contestId, @RequestBody(required = true) Contest contest) throws Exception {
        Result<Contest> contestResult = contestService.updateContest(contestId, contest);
        return new ResponseEntity(contestResult.getData(),HttpStatus.valueOf(contestResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Contest.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{contestId}")
    public ResponseEntity<Result<String>> deleteContestById(@PathVariable int contestId) throws Exception {
        Result<String> result = contestService.deleteContest(contestId);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
    }
}