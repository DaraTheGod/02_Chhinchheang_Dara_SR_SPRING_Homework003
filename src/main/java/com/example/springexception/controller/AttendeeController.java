package com.example.springexception.controller;

import com.example.springexception.model.entity.AttendeeModel;
import com.example.springexception.model.request.AttendeeCreateRequest;
import com.example.springexception.model.request.AttendeeUpdateRequest;
import com.example.springexception.model.response.ApiResponse;
import com.example.springexception.service.AttendeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendeeModel>>> getAttendees(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<AttendeeModel> attendees = attendeeService.getAllAttendees(page, size);
        ApiResponse<List<AttendeeModel>> response = ApiResponse.success("Retrieved attendees successfully", attendees);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{attendeeId}")
    public ResponseEntity<ApiResponse<AttendeeModel>> getAttendeeById(@PathVariable("attendeeId") Long attendeeId) {
        AttendeeModel attendee = attendeeService.getAttendeeById(attendeeId);
        ApiResponse<AttendeeModel> response = ApiResponse.success(String.format("Retrieved attendee with id %d successfully", attendeeId), attendee);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AttendeeModel>> saveAttendee(@Valid @RequestBody AttendeeCreateRequest request) {
        ApiResponse<AttendeeModel> response = ApiResponse.created("Created attendee successfully", attendeeService.saveAttendee(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{attendeeId}")
    public ResponseEntity<ApiResponse<AttendeeModel>> updateAttendeeById(
            @PathVariable("attendeeId") Long attendeeId,
            @Valid  @RequestBody AttendeeUpdateRequest request) {
        AttendeeModel attendee =  attendeeService.updateAttendeeById(attendeeId, request);
        ApiResponse<AttendeeModel> response = ApiResponse.success(String.format("Updated attendee with id %d successfully", attendeeId), attendee);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{attendeeId}")
    public ResponseEntity<ApiResponse<Void>> deleteAttendeeById(@PathVariable("attendeeId") Long attendeeId) {
        attendeeService.deleteAttendeeById(attendeeId);
        ApiResponse<Void> response = ApiResponse.success(String.format("Deleted attendee with id %d successfully", attendeeId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
