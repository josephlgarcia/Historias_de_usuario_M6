package com.events.eventManager.infrastructure.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.domain.ports.in.venue.CreateVenueUseCase;
import com.events.eventManager.domain.ports.in.venue.DeleteVenueUseCase;
import com.events.eventManager.domain.ports.in.venue.RetrieveVenueUseCase;
import com.events.eventManager.domain.ports.in.venue.UpdateVenueUseCase;
import com.events.eventManager.infrastructure.mappers.VenueMapper;
import com.events.eventManager.infrastructure.web.dto.venues.VenueRequest;
import com.events.eventManager.infrastructure.web.dto.venues.VenueResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Venues", description = "API for venue management")
@RestController
@RequestMapping("/api/v1/venue")
public class VenueController {

    private final CreateVenueUseCase createService;
    private final DeleteVenueUseCase deleteService;
    private final RetrieveVenueUseCase retrieveService;
    private final UpdateVenueUseCase updateService;
    private final VenueMapper mapper;

    public VenueController(CreateVenueUseCase createService, DeleteVenueUseCase deleteService,
            RetrieveVenueUseCase retrieveService, UpdateVenueUseCase updateService, VenueMapper mapper) {
        this.createService = createService;
        this.deleteService = deleteService;
        this.retrieveService = retrieveService;
        this.updateService = updateService;
        this.mapper = mapper;
    }


    @Operation(summary = "Create a new venue", description = "Create a new venue with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue created successfully",
            content = @Content(schema = @Schema(implementation = VenueResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VenueResponse> create(@Valid @RequestBody VenueRequest req) {
        Venue venue = mapper.requestToDomain(req);
        Venue saved = createService.createVenue(venue);
        VenueResponse response = mapper.domainToResponse(saved);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get venue by ID", description = "Returns a specific venue by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue found",
            content = @Content(schema = @Schema(implementation = VenueResponse.class))),
        @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getById(@PathVariable Long id) {
        Optional<Venue> venue = retrieveService.getVenueById(id);
        VenueResponse response = mapper.domainToResponse(venue.get());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update venue", description = "Updates the information of an existing venue")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue updated successfully",
            content = @Content(schema = @Schema(implementation = VenueResponse.class))),
        @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VenueResponse> update(@PathVariable Long id, @Valid @RequestBody VenueRequest req) {
        Venue venue = mapper.requestToDomain(req);
        Venue updated = updateService.updateVenue(id, venue);
        VenueResponse response = mapper.domainToResponse(updated);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List all venues", description = "Returns a list of all venues")
    @ApiResponse(responseCode = "200", description = "List of venues retrieved successfully",
        content = @Content(schema = @Schema(implementation = VenueResponse.class)))
    @GetMapping()
    public ResponseEntity<List<VenueResponse>> getAll() {
        List<Venue> venues = retrieveService.getAllVenues();
        List<VenueResponse> responses = venues.stream()
                .map(mapper::domainToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Delete venue", description = "Deletes a venue by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteService.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }

}
