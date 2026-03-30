package com.example.springexception.service.Impl;

import com.example.springexception.exception.BadRequestException;
import com.example.springexception.exception.ConflictException;
import com.example.springexception.exception.ResourceNotFoundException;
import com.example.springexception.model.entity.EventModel;
import com.example.springexception.model.request.EventRequest;
import com.example.springexception.repository.EventAttendeeRepository;
import com.example.springexception.repository.EventRepository;
import com.example.springexception.service.AttendeeService;
import com.example.springexception.service.EventService;
import com.example.springexception.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventAttendeeRepository eventAttendeeRepository;
    private final VenueService venueService;
    private final AttendeeService attendeeService;

    @Override
    public List<EventModel> getAllEvents(Integer page, Integer size) {
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
        int  offset = (page - 1) * size;
        return eventRepository.getAllEvents(offset, size);
    }

    @Override
    public EventModel getEventById(Long eventId) {
        validateId(eventId);
        EventModel event = eventRepository.getEventById(eventId);
        if (event == null) {
            throw new ResourceNotFoundException("Event with id " + eventId + " not found.");
        }
        return event;
    }

    @Transactional
    @Override
    public EventModel saveEvent(EventRequest request) {
        Map<String, String> errors = new HashMap<>();
        if (request.getEventName() == null || request.getEventName().trim().isEmpty()) {
            errors.put("eventName", "Event name cannot be blank");
        }
        if (request.getEventDate() == null || !request.getEventDate().isAfter(LocalDate.now())) {
            errors.put("eventDate", "Event date must be in the future");
        }
        if (request.getVenueId() == null || request.getVenueId() < 1) {
            errors.put("venueId", "Venue ID must be positive number");
        }
        for (int i = 0; i < request.getAttendeeIds().size(); i++) {
            if (request.getAttendeeIds().get(i) < 1) {
                errors.put("attendeeIds[" + i + "]", "Attendee ID must be positive number");
            }
        }
        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
        if (eventRepository.existsByEventNameAndEventDate(request.getEventName(), request.getEventDate())) {
            throw new ConflictException("Event name already exists on this date");
        }
        for (Long attendeeId : request.getAttendeeIds()) {
            attendeeService.getAttendeeById(attendeeId);
        }
        EventModel event = eventRepository.saveEvent(request);
        for (Long attendeeId : request.getAttendeeIds()) {
            attendeeService.getAttendeeById(attendeeId);
            eventAttendeeRepository.saveEventAndAttendee(attendeeId, event.getEventId());
        }
        return eventRepository.getEventById(event.getEventId());
    }

    @Override
    public EventModel updateEventById(Long eventId, EventRequest request) {
        Map<String, String> errors = new HashMap<>();
        if (eventId == null || eventId < 1) {
            errors.put("eventId", "must be greater than 0");
        }
        if (request.getEventName() == null || request.getEventName().trim().isEmpty()) {
            errors.put("eventName", "Event name cannot be blank");
        }
        if (request.getEventDate() == null || !request.getEventDate().isAfter(LocalDate.now())) {
            errors.put("eventDate", "Event date must be in the future");
        }
        if (request.getVenueId() == null || request.getVenueId() < 1) {
            errors.put("venueId", "Venue ID must be positive number");
        }
        for (int i = 0; i < request.getAttendeeIds().size(); i++) {
            if (request.getAttendeeIds().get(i) < 1) {
                errors.put("attendeeIds[" + i + "]", "Attendee ID must be positive number");
            }
        }
        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
        if (eventRepository.existsByEventNameAndEventDate(request.getEventName(), request.getEventDate())) {
            throw new ConflictException("Event name already exists on this date");
        }
        EventModel existingEvent = eventRepository.getEventById(eventId);
        if (existingEvent == null) {
            throw new ResourceNotFoundException("Event with id " + eventId + " not found.");
        }
        for (Long attendeeId : request.getAttendeeIds()) {
            attendeeService.getAttendeeById(attendeeId);
        }
        eventAttendeeRepository.deleteAllByEventId(eventId);
        eventRepository.updateEventById(eventId, request);
        for (Long attendeeId : request.getAttendeeIds()) {
            eventAttendeeRepository.saveEventAndAttendee(attendeeId, eventId);
        }
        return eventRepository.getEventById(eventId);
    }

    @Override
    public void deleteEventById(Long eventId) {
        validateId(eventId);
        EventModel event = eventRepository.deleteEventById(eventId);
        if (event == null) {
            throw new ResourceNotFoundException("Event with id " + eventId + " not found.");
        }
    }

    private void validateId(Long id) {
        if (id == null || id < 1) {
            Map<String, String> errors = new HashMap<>();
            errors.put("eventId", "must be greater than 0");
            throw new BadRequestException(errors);
        }
    }
}
