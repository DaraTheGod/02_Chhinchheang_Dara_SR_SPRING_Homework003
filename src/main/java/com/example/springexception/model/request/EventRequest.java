package com.example.springexception.model.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @NotBlank(message = "Event name cannot be blank")
    @Size(min = 5, max = 100, message = "Event name must be between 5 and 100 characters")
    private String eventName;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private Instant eventDate;

    @NotNull(message = "Venue ID is required")
    private Long venueId;

    private List<Long> attendeeIds;
}
