package com.example.springexception.service.Impl;

import com.example.springexception.exception.BadRequestException;
import com.example.springexception.exception.ConflictException;
import com.example.springexception.exception.ResourceNotFoundException;
import com.example.springexception.model.entity.VenueModel;
import com.example.springexception.model.request.VenueRequest;
import com.example.springexception.repository.VenueRepository;
import com.example.springexception.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Override
    public List<VenueModel> getAllVenues(Integer page, Integer size) {
        Map<String, String> errors = new HashMap<>();
        if (page == null || page < 1) {
            errors.put("page", "must be greater than 0");
        }
        if (size == null || size < 1) {
            errors.put("size", "must be greater than 0");
        }
        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
        int offset = (page - 1) * size;
        return venueRepository.getAllVenues(offset, size);
    }

    @Override
    public VenueModel getVenueById(Long venueId) {
        validateId(venueId);
        VenueModel venue = venueRepository.getVenueById(venueId);
        if (venue == null) {
            throw new ResourceNotFoundException("Venue with id " + venueId + " not found.");
        }
        return venue;
    }

    @Override
    public VenueModel saveVenue(VenueRequest request) {
        if (venueRepository.existsByName(request.getVenueName())) {
            throw new ConflictException("Venue name already exists");
        }
        return venueRepository.saveVenue(request);
    }

    @Override
    public VenueModel updateVenueById(Long venueId, VenueRequest request) {
        validateId(venueId);
        if (venueRepository.existsByNameAndIdNot(request.getVenueName(), venueId)) {
            throw new ConflictException("Venue name already exists");
        }
        VenueModel venue = venueRepository.updateVenueById(venueId, request);
        if (venue == null) {
            throw new ResourceNotFoundException("Venue with id " + venueId + " not found.");
        }
        return venue;
    }

    @Override
    public void deleteVenueById(Long venueId) {
        validateId(venueId);
        VenueModel venue = venueRepository.deleteVenueById(venueId);
        if (venue == null) {
            throw new ResourceNotFoundException("Venue with id " + venueId + " not found.");
        }
    }

    private void validateId(Long id) {
        if (id == null || id < 1) {
            Map<String, String> errors = new HashMap<>();
            errors.put("venueId", "must be greater than 0");
            throw new BadRequestException(errors);
        }
    }
}