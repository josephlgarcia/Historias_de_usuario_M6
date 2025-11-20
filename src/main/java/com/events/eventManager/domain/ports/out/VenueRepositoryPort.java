package com.events.eventManager.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.events.eventManager.domain.model.Venue;


public interface VenueRepositoryPort {
    Venue save(Venue venue);
    Optional<Venue> findById(Long Id);
    List<Venue> findAll();
    Optional<Venue> update(Venue venue);
    boolean deleteById(Long Id);
}
