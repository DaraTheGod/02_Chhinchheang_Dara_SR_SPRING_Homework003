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
public class VenueRequest {

    @NotBlank(message = "Venue name cannot be blank")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]*$",
            message = "Venue name must start with a letter and contain only letters, numbers and spaces")
    @Schema(defaultValue = "KSHRD")
    private String venueName;

    @NotBlank(message = "Location cannot be blank")
    private String location;
}
