package com.events.eventManager.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.eventManager.service.impl.VenueServiceImpl;
import com.events.eventManager.web.dto.VenueRequest;
import com.events.eventManager.web.dto.VenueResponse;

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

    private final VenueServiceImpl service;


    public VenueController(VenueServiceImpl service) {
        this.service = service;
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
        VenueResponse response = service.create(req);
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
        VenueResponse response = service.getById(id);
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
        VenueResponse response = service.update(id, req);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "List all venues", description = "Returns a list of all venues")
    @ApiResponse(responseCode = "200", description = "List of venues retrieved successfully",
        content = @Content(schema = @Schema(implementation = VenueResponse.class)))
    @GetMapping()
    public ResponseEntity<List<VenueResponse>> getAll() {
        List<VenueResponse> response = service.getAll();
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Delete venue", description = "Deletes a venue by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
