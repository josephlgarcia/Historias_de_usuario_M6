package com.events.eventManager.infrastructure.web.dto.venues;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request data for creating or updating a venue")
public record VenueRequest(

        @Schema(description = "Name of the venue", 
                example = "Madison Square Garden", 
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Venue name is required")
        String name,

        @Schema(description = "Physical address of the venue", 
                example = "4 Pennsylvania Plaza, New York, NY 10001", 
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Venue address is required")
        String address,

        @Schema(description = "Seating capacity of the venue", 
                example = "20000", 
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Venue capacity is required")
        @Min(value = 1, message = "Venue capacity must be at least 1")
        Integer capacity
) {}