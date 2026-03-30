package com.example.springexception.service;

import com.example.springexception.model.entity.EventModel;
import com.example.springexception.model.request.EventRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface EventService {
    List<EventModel> getAllEvents(Integer page, Integer size);

    EventModel getEventById(Long eventId);

    EventModel saveEvent(@Valid EventRequest request);

    EventModel updateEventById(Long eventId, @Valid EventRequest request);

    void deleteEventById(Long eventId);
}
