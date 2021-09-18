package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.PlayerType;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.PlayerTypeService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(path = "/player-types",produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerTypeController {

    @Autowired
    PlayerTypeService playerTypeService;

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PlayerType.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerType>> getAllPlayerType() {
        Result<List<PlayerType>> playerTypeList = playerTypeService.findAllPlayerType();
        return new ResponseEntity<>(playerTypeList.getData(), HttpStatus.valueOf(playerTypeList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PlayerType.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/{playerTypeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerType> getPlayerTypeById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int playerTypeId) throws Exception {
        Result<PlayerType> playerTypeList = playerTypeService.findPlayerTypeById(playerTypeId);
        return new ResponseEntity<>(playerTypeList.getData(), HttpStatus.valueOf(playerTypeList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = PlayerType.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerType> addPlayerType(@RequestBody(required = true) @Valid PlayerType PlayerType) throws  Exception {
        Result<PlayerType> playerTypeResult = playerTypeService.addPlayerType(PlayerType);
        return new ResponseEntity(playerTypeResult.getData(), HttpStatus.valueOf(playerTypeResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = PlayerType.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PutMapping(value = "/{playerTypeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerType> updatePlayerType(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int playerTypeId,@RequestBody(required = true) @Valid PlayerType PlayerType) throws Exception {
        Result<PlayerType> playerTypeResult = playerTypeService.updatePlayerType(playerTypeId, PlayerType);
        return new ResponseEntity(playerTypeResult.getData(), HttpStatus.valueOf(playerTypeResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = PlayerType.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping(value = "/{playerTypeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<String>> deletePlayerTypeById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int playerTypeId) throws Exception {
        Result<String> result =  playerTypeService.deletePlayerType(playerTypeId);
        return new ResponseEntity(result, HttpStatus.valueOf(result.getCode()));
    }
}
