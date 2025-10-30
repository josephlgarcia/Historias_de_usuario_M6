package com.events.eventManager.web.dto;

import java.time.LocalDateTime;

public class EventResponse {
    
    private Long id;
    private String name;
    private LocalDateTime date;
    private Long venueId;

    public EventResponse(Long id, String name, LocalDateTime date, Long venueId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.venueId = venueId;
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

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }
}
