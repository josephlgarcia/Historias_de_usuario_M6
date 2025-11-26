package com.events.eventManager.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.events.eventManager.domain.model.Venue;


public interface VenueRepositoryPort {
    Venue save(Venue venue);
    Optional<Venue> findById(Long id);
    List<Venue> findAll();
    void deleteById(Long id);
    boolean existsByNameIgnoreCase(String name);
    boolean existsById(Long id);
}
