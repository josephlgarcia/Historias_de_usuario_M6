package com.events.eventManager.web.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response data for an event")
public class EventResponse {
    
    @Schema(description = "Unique event ID", 
            example = "1")
    private Long id;

    @Schema(description = "Event name", 
            example = "Rock concert")
    private String name;

    @Schema(description = "Date and time of the event", 
            example = "2025-12-31T20:00:00")
    private LocalDateTime date;

    @Schema(description = "Venue details for the event", 
            example = "{ 'id': 1, 'name': 'Grand Hall', 'address': '123 Main St' }")
    private VenueResponse venue;

    public EventResponse() {
    }

    public EventResponse(Long id, String name, LocalDateTime date, VenueResponse venue) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.venue = venue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public VenueResponse getVenue() {
        return venue;
    }

    public void setVenue(VenueResponse venue) {
        this.venue = venue;
    }
}
