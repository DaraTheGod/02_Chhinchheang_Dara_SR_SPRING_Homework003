package com.example.springexception.service.Impl;

import com.example.springexception.exception.BadRequestException;
import com.example.springexception.exception.ConflictException;
import com.example.springexception.exception.ResourceNotFoundException;
import com.example.springexception.model.entity.AttendeeModel;
import com.example.springexception.model.request.AttendeeCreateRequest;
import com.example.springexception.model.request.AttendeeUpdateRequest;
import com.example.springexception.repository.AttendeeRepository;
import com.example.springexception.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendeeServiceImpl implements AttendeeService {

    private final AttendeeRepository attendeeRepository;

    @Override
    public List<AttendeeModel> getAllAttendees(Integer page, Integer size) {
        Map<String, String> errors = new HashMap<>();
        if (page < 1) {
            errors.put("page", "must be greater than 0");
        }
        if (size < 1) {
            errors.put("size", "must be greater than 0");
        }
        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
        int offset = (page - 1) * size;
        return attendeeRepository.getAllAttendees(offset, size);
    }

    @Override
    public AttendeeModel getAttendeeById(Long attendeeId) {
        validateId(attendeeId);
        AttendeeModel attendee = attendeeRepository.getAttendeeById(attendeeId);
        if (attendee == null) {
            throw new ResourceNotFoundException("Attendee with id " + attendeeId + " not found.");
        }
        return attendee;
    }

    @Override
    public AttendeeModel saveAttendee(AttendeeCreateRequest request) {
        if (attendeeRepository.existsByName(request.getAttendeeName())) {
            throw new ConflictException("Attendee name already exists");
        }
        if (attendeeRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Attendee email already exists");
        }
        return attendeeRepository.saveAttendee(request);
    }

    @Override
    public AttendeeModel updateAttendeeById(Long attendeeId, AttendeeUpdateRequest request) {
        validateId(attendeeId);
        if (attendeeRepository.getNameById(request.getAttendeeName(), attendeeId)) {
            throw new ConflictException("Attendee name already exists");
        }
        AttendeeModel attendee = attendeeRepository.updateAttendeeById(attendeeId, request);
        if (attendee == null) {
            throw new ResourceNotFoundException("Attendee with id " + attendeeId + " not found.");
        }
        return attendee;
    }

    @Override
    public void deleteAttendeeById(Long attendeeId) {
        validateId(attendeeId);
        AttendeeModel attendee = attendeeRepository.deleteAttendeeById(attendeeId);
        if (attendee == null) {
            throw new ResourceNotFoundException("Attendee with id " + attendeeId + " not found.");
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
