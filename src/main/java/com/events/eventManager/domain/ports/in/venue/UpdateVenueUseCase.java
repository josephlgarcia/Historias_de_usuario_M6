package com.events.eventManager.domain.ports.in.venue;

import com.events.eventManager.domain.model.Venue;

public interface UpdateVenueUseCase {
    Venue updateVenue(Long id, Venue venue);
}
