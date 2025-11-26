package com.events.eventManager.infrastructure.web.controller;

import org.apache.el.stream.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.domain.ports.in.event.CreateEventUseCase;
import com.events.eventManager.domain.ports.in.event.DeleteEventUseCase;
import com.events.eventManager.domain.ports.in.event.RetrieveEventUseCase;
import com.events.eventManager.domain.ports.in.event.UpdateEventUseCase;
import com.events.eventManager.infrastructure.mappers.EventMapper;
import com.events.eventManager.infrastructure.web.dto.events.EventRequest;
import com.events.eventManager.infrastructure.web.dto.events.EventResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.experimental.var;

@Tag(name = "Events", description = "API for event management")
@RestController
@RequestMapping("/api/v1/event")
public class EventController {
    private final CreateEventUseCase createService;
    private final DeleteEventUseCase deleteService;
    private final RetrieveEventUseCase retrieveService;
    private final UpdateEventUseCase updateService;
    private final EventMapper mapper;

    public EventController(CreateEventUseCase createService, DeleteEventUseCase deleteService,
            RetrieveEventUseCase retrieveService, UpdateEventUseCase updateService, EventMapper mapper) {
        this.createService = createService;
        this.deleteService = deleteService;
        this.retrieveService = retrieveService;
        this.updateService = updateService;
        this.mapper = mapper;
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
        var event = mapper.requestToDomain(req);
        var saved = createService.createEvent(event);
        EventResponse response = mapper.domainToResponse(saved);
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
        var event = retrieveService.getEventById(id);
        EventResponse response = mapper.domainToResponse(event.get());
        return ResponseEntity.ok(response);
    }
}
