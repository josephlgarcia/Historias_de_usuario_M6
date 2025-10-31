package com.events.eventManager.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request data for creating or updating a venue")
public class VenueRequest {

    @Schema(description = "Name of the venue", 
            example = "Madison Square Garden", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Venue name is required")
    private String name;


    @Schema(description = "Physical address of the venue", 
            example = "4 Pennsylvania Plaza, New York, NY 10001", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Venue address is required")
    private String address;

    @Schema(description = "Seating capacity of the venue", 
            example = "20000", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Venue capacity is required")
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
