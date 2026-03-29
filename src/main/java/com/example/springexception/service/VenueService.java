package com.example.springexception.service;

import com.example.springexception.model.entity.VenueModel;
import com.example.springexception.model.request.VenueRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface VenueService {
    List<VenueModel> getAllVenues(Integer page, Integer size);

    VenueModel getVenueById(Long venueId);

    VenueModel saveVenue(@Valid VenueRequest request);

    VenueModel updateVenueById(Long venueId, @Valid VenueRequest request);

    void deleteVenueById(Long venueId);
}
