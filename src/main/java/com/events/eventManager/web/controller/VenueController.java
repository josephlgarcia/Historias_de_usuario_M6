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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/venue")
public class VenueController {

    private final VenueServiceImpl service;


    public VenueController(VenueServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VenueResponse> create(@Valid @RequestBody VenueRequest req) {
        VenueResponse response = service.create(req);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getById(@PathVariable Long id) {
        VenueResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueResponse> update(@PathVariable Long id, @Valid @RequestBody VenueRequest req) {
        VenueResponse response = service.update(id, req);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<VenueResponse>> getAll() {
        List<VenueResponse> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
