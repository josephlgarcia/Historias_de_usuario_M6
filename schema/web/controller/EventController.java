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

import com.events.eventManager.service.impl.EventServiceImpl;
import com.events.eventManager.web.dto.EventRequest;
import com.events.eventManager.web.dto.EventResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Events", description = "API for event management")
@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventServiceImpl service;


    public EventController(EventServiceImpl service) {
        this.service = service;
    }


    @Operation(summary = "Create a new event", description = "Create a new event with the information provided")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event created successfully",
            content = @Content(schema = @Schema(implementation = EventResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest req) {
        EventResponse response = service.create(req);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Get event by ID", description = "Returns a specific event by its identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event found",
            content = @Content(schema = @Schema(implementation = EventResponse.class))),
        @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        EventResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Update event", description = "Updates the information of an existing event")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event updated successfully",
            content = @Content(schema = @Schema(implementation = EventResponse.class))),
        @ApiResponse(responseCode = "404", description = "Event not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable Long id, @Valid @RequestBody EventRequest req) {
        EventResponse response = service.update(id, req);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "List all events", description = "Returns a list of all events")
    @ApiResponse(responseCode = "200", description = "List of events retrieved successfully")
    @GetMapping()
    public ResponseEntity<List<EventResponse>> getAll() {
        List<EventResponse> response = service.getAll();
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Delete event", description = "Deletes an event by its identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
