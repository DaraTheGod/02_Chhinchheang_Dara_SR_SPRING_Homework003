package com.example.springexception.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
public class AttendeeCreateRequest {
    @NotBlank(message = "Attendee name cannot be blank")
    @Size(min = 3, max = 50, message = "Attendee name must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Attendee name can only contain letters and spaces")
    @Schema(defaultValue = "Guest User")
    private String attendeeName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid format (e.g., abc@gmail.com)")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    private String email;
}
