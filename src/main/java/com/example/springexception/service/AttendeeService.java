package com.example.springexception.service;

import com.example.springexception.model.entity.AttendeeModel;
import com.example.springexception.model.request.AttendeeCreateRequest;
import com.example.springexception.model.request.AttendeeUpdateRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface AttendeeService {
    List<AttendeeModel> getAllAttendees(Integer page, Integer size);

    AttendeeModel getAttendeeById(Long attendeeId);

    AttendeeModel saveAttendee(@Valid AttendeeCreateRequest request);

    AttendeeModel updateAttendeeById(Long attendeeId, @Valid AttendeeUpdateRequest request);

    void deleteAttendeeById(Long attendeeId);
}
