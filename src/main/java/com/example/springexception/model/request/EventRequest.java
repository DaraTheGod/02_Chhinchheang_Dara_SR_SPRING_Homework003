package com.example.springexception.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @Schema(defaultValue = "KSHRD Party")
    private String eventName;

    private LocalDate eventDate;

    @NotNull(message = "Venue ID is required")
    private Long venueId;

    private List<Long> attendeeIds;
}
