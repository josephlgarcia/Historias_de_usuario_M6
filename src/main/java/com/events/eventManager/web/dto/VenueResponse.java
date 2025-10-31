package com.events.eventManager.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response data of a venue")
public class VenueResponse {

    @Schema(description = "Unique venue ID", example = "1")
    private Long id;

    @Schema(description = "Name of the venue", example = "Madison Square Garden")
    private String name;

    @Schema(description = "Physical address of the venue", example = "4 Pennsylvania Plaza, New York, NY 10001")
    private String address;

    @Schema(description = "Seating capacity of the venue", example = "20000")
    private Integer capacity;

    public VenueResponse(Long id, String name, String address, Integer capacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}
