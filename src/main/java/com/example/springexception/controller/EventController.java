package com.example.springexception.controller;

import com.example.springexception.model.entity.EventModel;
import com.example.springexception.model.request.EventRequest;
import com.example.springexception.model.response.ApiResponse;
import com.example.springexception.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventModel>>> getEvents(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<EventModel> events = eventService.getAllEvents(page, size);
        ApiResponse<List<EventModel>> response = ApiResponse.success("Retrieved events successfully", events);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventModel>> getEventById(@PathVariable("eventId") Long eventId) {
        EventModel event = eventService.getEventById(eventId);
        ApiResponse<EventModel> response = ApiResponse.success(String.format("Retrieved event with id %d successfully", eventId), event);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EventModel>> saveEvent(@Valid @RequestBody EventRequest request) {
        ApiResponse<EventModel> response = ApiResponse.created("Created event successfully", eventService.saveEvent(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventModel>> updateEventById(@PathVariable("eventId") Long eventId,@Valid  @RequestBody EventRequest request) {
        EventModel event = eventService.updateEventById(eventId, request);
        ApiResponse<EventModel> response = ApiResponse.success(String.format("Updated event with id %d successfully", eventId), event);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse<Void>> deleteEventById(@PathVariable("eventId") Long eventId) {
        eventService.deleteEventById(eventId);
        ApiResponse<Void> response = ApiResponse.success(String.format("Deleted event with id %d successfully", eventId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
