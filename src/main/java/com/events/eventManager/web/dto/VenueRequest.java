package com.events.eventManager.web.dto;

import jakarta.validation.constraints.NotBlank;

public class VenueRequest {

    @NotBlank(message = "Venue name is required")
    private String name;

    @NotBlank(message = "Venue address is required")
    private String address;

    @NotBlank(message = "Venue capacity is required")
    private Integer capacity;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
