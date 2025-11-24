package com.events.eventManager.domain.ports.in.venue;

import java.util.Optional;

import com.events.eventManager.domain.model.Venue;

public interface CreateVenueUseCase {
    Optional<Venue> createVenue(Venue venue);
}
