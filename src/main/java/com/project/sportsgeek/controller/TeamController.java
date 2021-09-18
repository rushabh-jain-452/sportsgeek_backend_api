package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Team;
import com.project.sportsgeek.model.profile.User;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.TeamService;
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
@RequestMapping(path = "/teams",produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {

    @Autowired
    TeamService teamService;

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Team.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Team>> getAllTeam() {
        Result<List<Team>> teamList = teamService.findAllTeam();
        return new ResponseEntity<>(teamList.getData(), HttpStatus.valueOf(teamList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Team.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/{teamId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> getTeamById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int teamId) throws Exception {
        Result<Team> teamList = teamService.findTeamById(teamId);
        return new ResponseEntity<>(teamList.getData(), HttpStatus.valueOf(teamList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Team.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
//    @RequestBody(required = true) @Valid Team team
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> addTeam (@RequestParam("name") String name, @RequestParam("shortName") String shortName, @RequestParam(value="teamLogo", required = false) MultipartFile multipartFile) throws  Exception {
        Team team = new Team();
        team.setName(name);
        team.setShortName(shortName);

        Result<Team> teamResult = teamService.addTeam(team,multipartFile);
        return new ResponseEntity(teamResult.getData(), HttpStatus.valueOf(teamResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Team.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
//    @RequestBody(required = true) @Valid Team team
    @PutMapping(value = "/{teamId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> updateTeam(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int teamId, @RequestParam("name") String name, @RequestParam("shortName") String shortName, @RequestParam(value="teamLogo", required = false) MultipartFile multipartFile) throws Exception {
        Team team = new Team();
        team.setName(name);
        team.setShortName(shortName);

        Result<Team> teamResult = teamService.updateTeam(teamId, team, multipartFile);
        return new ResponseEntity(teamResult.getData(), HttpStatus.valueOf(teamResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Team.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping(value = "/{teamId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<String>> deleteTeamById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int teamId) throws Exception {
        Result<Integer> teamResult =  teamService.deleteTeam(teamId);
        return new ResponseEntity(teamResult, HttpStatus.valueOf(teamResult.getCode()));
    }
}
