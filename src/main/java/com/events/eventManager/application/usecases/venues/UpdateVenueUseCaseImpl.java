package com.events.eventManager.application.usecases.venues;

import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.domain.ports.in.venue.UpdateVenueUseCase;
import com.events.eventManager.domain.ports.out.VenueRepositoryPort;

public class UpdateVenueUseCaseImpl implements UpdateVenueUseCase {

    private final VenueRepositoryPort repo;

    public UpdateVenueUseCaseImpl(VenueRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public Venue updateVenue(Long id, Venue venue) {
        if (repo.existsByNameIgnoreCase(venue.getName())) {
            throw new IllegalArgumentException("Venue with name '" + venue.getName() + "' already exists.");
        }
        return repo.save(venue);
    }

}
