package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Recharge;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.RechargeService;
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
//@RequestMapping(path = "/recharge",produces = MediaType.APPLICATION_JSON_VALUE)
public class RechargeController {
    @Autowired
    RechargeService rechargeService;

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Recharge.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(path = "/recharge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Recharge>> getAllRecharge() {
        Result<List<Recharge>> rechargeList = rechargeService.findAllRecharge();
        return new ResponseEntity<>(rechargeList.getData(), HttpStatus.valueOf(rechargeList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Recharge.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(path = "/recharge/{rechargeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recharge> getRechargeByRechargeId(@PathVariable int rechargeId) throws Exception {
        Result<Recharge> rechargeList = rechargeService.findRechargeByRechargeId(rechargeId);
        return new ResponseEntity<>(rechargeList.getData(), HttpStatus.valueOf(rechargeList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Recharge.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(path = "/users/{userId}/recharge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Recharge>> getRechargeByUserId(@PathVariable int userId) throws Exception {
        Result<List<Recharge>> rechargeList = rechargeService.findRechargeByUserId(userId);
        return new ResponseEntity<>(rechargeList.getData(), HttpStatus.valueOf(rechargeList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Recharge.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PostMapping(path = "/recharge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recharge> addRecharge(@RequestBody(required = true) Recharge recharge) throws  Exception {
        Result<Recharge> rechargeResult = rechargeService.addRecharge(recharge);
        return new ResponseEntity(rechargeResult.getData(), HttpStatus.valueOf(rechargeResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Recharge.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PutMapping(value = "/recharge/{rechargeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recharge> updateRecharge(@PathVariable int rechargeId,@RequestBody(required = true) Recharge recharge) throws Exception {
        Result<Recharge> rechargeResult = rechargeService.updateRecharge(rechargeId, recharge);
        return new ResponseEntity(rechargeResult.getData(), HttpStatus.valueOf(rechargeResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Recharge.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class)
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping(value = "/recharge/{rechargeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<String>> deleteRechargeById(@PathVariable int rechargeId) throws Exception {
        Result<String> result =  rechargeService.deleteRecharge(rechargeId);
        return new ResponseEntity(result, HttpStatus.valueOf(result.getCode()));
    }
}
