package com.project.sportsgeek.controller;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Venue;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.VenueService;
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
@RequestMapping(path = "/venues",produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueController {
    @Autowired
    VenueService venueService;

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Venue>> getAllVenue() {
        Result<List<Venue>> VenueList = venueService.findAllVenue();
        return new ResponseEntity<>(VenueList.getData(), HttpStatus.valueOf(VenueList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/{venueId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Venue> getVenueById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int venueId) throws Exception {
        Result<Venue> VenueList = venueService.findVenueById(venueId);
        return new ResponseEntity<>(VenueList.getData(), HttpStatus.valueOf(VenueList.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Venue> addVenue(@RequestBody(required = true) @Valid Venue venue) throws Exception {
        Result<Venue> VenueResult = venueService.addVenue(venue);
        return new ResponseEntity(VenueResult.getData(), HttpStatus.valueOf(VenueResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "success", response = Venue.class),
                    @ApiResponse(code = 400, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @PutMapping(value = "/{venueId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Venue> updateVenue(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int venueId, @RequestBody(required = true) @Valid Venue venue) throws Exception {
        Result<Venue> VenueResult = venueService.updateVenue(venueId, venue);
        return new ResponseEntity(VenueResult.getData(), HttpStatus.valueOf(VenueResult.getCode()));
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "success", response = Venue.class),
                    @ApiResponse(code = 404, message = "Bad request", response = ResultException.class),
                    @ApiResponse(code = 500, message = "Unfortunately there is technical error while processing your request", response = ResultException.class),
                    @ApiResponse(code = 403 , message = "Forbidden!! Access is Denied!")
            }
    )
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping(value = "/{venueId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<String>> deleteVenueById(@PathVariable @Valid @Pattern(regexp = "[0-9]*") int venueId) throws Exception {
        Result<String> result =  venueService.deleteVenue(venueId);
        return new ResponseEntity(result, HttpStatus.valueOf(result.getCode()));
    }
}
