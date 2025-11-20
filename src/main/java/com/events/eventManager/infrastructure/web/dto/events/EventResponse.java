package com.events.eventManager.infrastructure.web.dto.events;

import java.time.LocalDateTime;

import com.events.eventManager.infrastructure.web.dto.venues.VenueResponse;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Response data for an event")
public record EventResponse(
        @Schema(description = "Unique event ID", example = "1")
        Long id,

        @Schema(description = "Event name", example = "Rock concert")
        String name,

        @Schema(description = "Date and time of the event", example = "2025-12-31T20:00:00")
        LocalDateTime date,

        @Schema(description = "Venue details for the event", example = "{ 'id': 1, 'name': 'Grand Hall', 'address': '123 Main St' }")
        VenueResponse venue
) {}
