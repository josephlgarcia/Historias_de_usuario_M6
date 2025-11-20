package com.events.eventManager.domain.ports.in.venue;

import java.util.List;
import java.util.Optional;

import com.events.eventManager.domain.model.Venue;

public interface RetrieveVenueUseCase {
    Optional<Venue> getVenueById(Long id);
    List<Venue> getAllVenues();
}
