package com.events.eventManager.infrastructure.web.dto.events;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request data to create or update an event")
public record EventRequest(

        @Schema(description = "Name of the event",
                example = "Rock Concert",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Event name is required")
        String name,

        @Schema(description = "Date and time of the event",
                example = "2025-12-31T20:00:00",
                requiredMode = Schema.RequiredMode.REQUIRED)
                @Future(message = "Event date must be in the future")
        @NotNull(message = "Event date is required")
        LocalDateTime date,

        @Schema(description = "ID of the venue where the event will take place",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Venue id is required")
        Long venueId
) {}
