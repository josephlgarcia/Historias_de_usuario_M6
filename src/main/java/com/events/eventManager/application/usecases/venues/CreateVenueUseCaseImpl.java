package com.events.eventManager.application.usecases.venues;

import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.domain.ports.in.venue.CreateVenueUseCase;
import com.events.eventManager.domain.ports.out.VenueRepositoryPort;

import jakarta.transaction.Transactional;

public class CreateVenueUseCaseImpl implements CreateVenueUseCase {

    private final VenueRepositoryPort repo;

    public CreateVenueUseCaseImpl(VenueRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public Venue createVenue(Venue venue) {

        if (repo.existsByNameIgnoreCase(venue.getName())) {
            throw new IllegalArgumentException("Event with name '" + venue.getName() + "' already exists.");
        }

        return repo.save(venue);
    }

}
