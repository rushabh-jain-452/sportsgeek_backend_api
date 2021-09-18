package com.project.sportsgeek.service;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Venue;
import com.project.sportsgeek.repository.venuerepo.VenueRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VenueService {
    @Autowired
    @Qualifier(value = "venueRepo")
    VenueRepository venueRepository;

    public Result<List<Venue>> findAllVenue() {
        List<Venue> VenueList = venueRepository.findAllVenue();
        return new Result<>(200,VenueList);
    }

    public Result<Venue> findVenueById(int venueId) throws Exception {
        Venue venue = venueRepository.findVenueById(venueId);
        if (venue != null) {
            return new Result<>(200, venue);
        }
        throw new ResultException(new Result<>(404, "Venue with VenueId: " + venueId + " not found."));
    }

    public Result<Venue> addVenue(Venue venue) throws Exception {
        int venueId = venueRepository.addVenue(venue);
        venue.setVenueId(venueId);
        if (venueId > 0) {
            return new Result<>(201, venue);
        }
        throw new ResultException(new Result<>(400, "Unable to add Venue."));
    }

    public Result<Venue> updateVenue(int venueId, Venue venue) throws Exception {
        if (venueRepository.updateVenue(venueId,venue)) {
            return new Result<>(200, venue);
        }
        throw new ResultException(new Result<>(404, "Venue with VenueId: " + venueId + " not found."));
    }

    public Result<String> deleteVenue(int venueId) throws Exception{
        if (venueRepository.deleteVenue(venueId)) {
            return new Result<>(200,"Venue Deleted Successfully");
        }
        throw new ResultException(new Result<>(404, "Venue with VenueId: " + venueId + " not found."));
    }
}
