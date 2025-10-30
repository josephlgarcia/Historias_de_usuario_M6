package com.events.eventManager.web.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;

public class EventRequest {
    
    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Event date is required")
    private LocalDateTime date;

    @NotBlank(message = "Event location is required")
    private String location;

    @NotBlank(message = "Venue id is required")
    private Long venueId;
    
    
    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
