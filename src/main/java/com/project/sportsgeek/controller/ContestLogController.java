package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.*;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.ContestLogService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(path = "/contest-log",produces = MediaType.APPLICATION_JSON_VALUE)
public class ContestLogController {

    @Autowired
    ContestLogService contestLogService;

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/last-days/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContestLogWithUser>> getAllContestLogForLastDays(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int days) {
        if(days > 30){
            days = 30;  // Set Max days
        }
        Result<List<ContestLogWithUser>> contestLogList = contestLogService.findAllContestLogForLastDays(days);
        return new ResponseEntity<>(contestLogList.getData(), HttpStatus.valueOf(contestLogList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/after-id/{contestLogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContestLogWithUser>> getAllContestLogAfterId(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int contestLogId) {
        Result<List<ContestLogWithUser>> contestLogList = contestLogService.findAllContestLogAfterId(contestLogId);
        return new ResponseEntity<>(contestLogList.getData(), HttpStatus.valueOf(contestLogList.getCode()));
    }

    // Formatted
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = ContestLog.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/formatted/last-days/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContestLogFormatted>> getAllContestLogFormattedForLastDays(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int days) {
        if(days > 30){
            days = 30;  // Set Max days
        }
        Result<List<ContestLogFormatted>> contestLogList = contestLogService.findAllContestLogFormattedForLastDays(days);
        return new ResponseEntity<>(contestLogList.getData(), HttpStatus.valueOf(contestLogList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PublicChat.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/formatted/after-id/{contestLogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContestLogFormatted>> getAllContestLogFormattedAfterId(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int contestLogId) {
        Result<List<ContestLogFormatted>> contestLogList = contestLogService.findAllContestLogFormattedAfterId(contestLogId);
        return new ResponseEntity<>(contestLogList.getData(), HttpStatus.valueOf(contestLogList.getCode()));
    }
}
