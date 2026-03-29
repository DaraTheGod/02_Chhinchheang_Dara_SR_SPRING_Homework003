package com.example.springexception.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventModel {
    private Long eventId;
    private String eventName;
    private Instant eventDate;
    private VenueModel venue;
    private List<AttendeeModel> attendees;
}
