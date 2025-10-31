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


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventServiceImpl service;


    public EventController(EventServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest req) {
        EventResponse response = service.create(req);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        EventResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable Long id, @Valid @RequestBody EventRequest req) {
        EventResponse response = service.update(id, req);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<EventResponse>> getAll() {
        List<EventResponse> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
