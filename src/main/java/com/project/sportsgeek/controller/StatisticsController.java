package com.project.sportsgeek.controller;


import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Contest;
import com.project.sportsgeek.model.Statistics;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.StatisticsService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping(path = "/statistics",produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Statistics.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/users/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Statistics>> getAllStatistics() {
        Result<List<Statistics>> statList = statisticsService.findAllStatistics();
        return new ResponseEntity<>(statList.getData(), HttpStatus.valueOf(statList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Statistics.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/users/future-contest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contest>> getAllFutureContests() {
        Result<List<Contest>> contestList = statisticsService.findFutureContests();
        return new ResponseEntity<>(contestList.getData(), HttpStatus.valueOf(contestList.getCode()));
    }
}
