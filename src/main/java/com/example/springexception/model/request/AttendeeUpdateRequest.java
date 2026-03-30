package com.example.springexception.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeUpdateRequest {
    @NotBlank(message = "Attendee name cannot be blank")
    @Size(min = 1, max = 50, message = "Attendee name must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Attendee name can only contain letters and spaces")
    @Schema(defaultValue = "Guest User")
    private String attendeeName;
}
