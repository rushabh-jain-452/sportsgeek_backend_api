package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Player;
import com.project.sportsgeek.model.PlayerResponse;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.PlayerService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(path = "/players",produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Player.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerResponse>> getAllPlayer() {
        Result<List<PlayerResponse>> playerList = playerService.findAllPlayer();
        return new ResponseEntity<>(playerList.getData(), HttpStatus.valueOf(playerList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Player.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/{playerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int playerId) throws Exception {
        Result<PlayerResponse> playerResult = playerService.findPlayerById(playerId);
        return new ResponseEntity<>(playerResult.getData(), HttpStatus.valueOf(playerResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Player.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/player-type/{playerTypeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerResponse>> getPlayerByPlayerType(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int playerTypeId) throws Exception {
        Result<List<PlayerResponse>> playerResult = playerService.findPlayerByPlayerType(playerTypeId);
        return new ResponseEntity<>(playerResult.getData(), HttpStatus.valueOf(playerResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Player.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping(value = "/team/{teamId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerResponse>> getPlayerByTeamId(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int teamId) throws Exception {
        Result<List<PlayerResponse>> playerResult = playerService.findPlayerByTeamId(teamId);
        return new ResponseEntity<>(playerResult.getData(), HttpStatus.valueOf(playerResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Player.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
//    @RequestBody(required = true) @Valid Player player
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> addPlayer(@RequestParam("playerId") int playerId, @RequestParam("teamId") int teamId, @RequestParam("name") String name, @RequestParam("typeId") int typeId, @RequestParam(value="profilePicture", required = false) MultipartFile multipartFile) throws Exception {
        Player player = new Player();
        player.setPlayerId(playerId);
        player.setTeamId(teamId);
        player.setName(name);
        player.setTypeId(typeId);

        Result<Player> playerResult = playerService.addPlayer(player, multipartFile);
        return new ResponseEntity(playerResult.getData(), HttpStatus.valueOf(playerResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Player.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
//    @RequestBody(required = true) @Valid Player player
    @PutMapping(value = "/{playerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> updatePlayer(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int playerId, @RequestParam("teamId") int teamId, @RequestParam("name") String name, @RequestParam("typeId") int typeId, @RequestParam(value="profilePicture", required = false) MultipartFile multipartFile) throws Exception {
        Player player = new Player();
        player.setPlayerId(playerId);
        player.setTeamId(teamId);
        player.setName(name);
        player.setTypeId(typeId);

        Result<Player> playerResult = playerService.updatePlayer(playerId, player, multipartFile);
        return new ResponseEntity(playerResult.getData(), HttpStatus.valueOf(playerResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Player.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PutMapping(value = "/{playerId}/update-player-type/{playerTypeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<String>> updatePlayerType(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int playerId, @PathVariable @Valid @Pattern(regexp = "[0-9]*") int playerTypeId) throws Exception {
        Result<String> playerResult = playerService.updatePlayerType(playerId, playerTypeId);
        return new ResponseEntity(playerResult, HttpStatus.valueOf(playerResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Player.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping(value = "/{playerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<String>> deletePlayerById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int playerId) throws Exception {
        Result<String> result =  playerService.deletePlayer(playerId);
        return new ResponseEntity(result, HttpStatus.valueOf(result.getCode()));
    }
}
