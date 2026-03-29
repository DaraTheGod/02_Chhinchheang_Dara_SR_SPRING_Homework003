package com.example.springexception.controller;

import com.example.springexception.model.entity.VenueModel;
import com.example.springexception.model.request.VenueRequest;
import com.example.springexception.model.response.ApiResponse;
import com.example.springexception.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<VenueModel>>> getVenues(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<VenueModel> venues = venueService.getAllVenues(page, size);
        ApiResponse<List<VenueModel>> response = ApiResponse.success("Retrieved venues successfully", venues);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{venueId}")
    public ResponseEntity<ApiResponse<VenueModel>> getVenueById(@PathVariable("venueId") Long venueId) {
        VenueModel venue = venueService.getVenueById(venueId);
        ApiResponse<VenueModel> response = ApiResponse.success(String.format("Retrieved venue with id %d successfully", venueId), venue);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VenueModel>> saveVenue(@Valid @RequestBody VenueRequest request) {
        ApiResponse<VenueModel> response = ApiResponse.created("Created venue successfully", venueService.saveVenue(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{venueId}")
    public ResponseEntity<ApiResponse<VenueModel>> updateVenueById(
            @PathVariable("venueId") Long venueId,
            @Valid @RequestBody VenueRequest request) {
        VenueModel venue = venueService.updateVenueById(venueId, request);
        ApiResponse<VenueModel> response = ApiResponse.success(String.format("Updated venue with id %d successfully", venueId), venue);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{venueId}")
    public ResponseEntity<ApiResponse<Void>> deleteVenueById(@PathVariable("venueId") Long venueId) {
        venueService.deleteVenueById(venueId);
        ApiResponse<Void> response = ApiResponse.success(String.format("Deleted venue with id %d successfully", venueId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
