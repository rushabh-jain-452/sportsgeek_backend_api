package com.project.sportsgeek.repository.venuerepo;

import com.project.sportsgeek.model.Venue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "venueRepo")
public interface VenueRepository {

    List<Venue> findAllVenue();

    Venue findVenueById(int venueId) throws Exception;

    int addVenue(Venue venue) throws Exception;

    boolean updateVenue(int venueId, Venue venue) throws Exception;

    boolean deleteVenue(int venueId) throws Exception;

}
