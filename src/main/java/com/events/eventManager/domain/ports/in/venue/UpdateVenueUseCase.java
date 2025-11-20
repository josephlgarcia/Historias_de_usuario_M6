package com.events.eventManager.domain.ports.in.venue;

import java.util.Optional;

import com.events.eventManager.domain.model.Venue;

public interface UpdateVenueUseCase {
    Optional<Venue> updateVenue(Long venueId, Venue Venue);
}
