package com.events.eventManager.domain.ports.in.venue;

import com.events.eventManager.domain.model.Venue;

public interface CreateVenueUseCase {
    Venue createVenue(Venue venue);
}
