package com.events.eventManager.application.usecases.venues;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.domain.ports.in.venue.UpdateVenueUseCase;
import com.events.eventManager.domain.ports.out.VenueRepositoryPort;

@Service
public class UpdateVenueUseCaseImpl implements UpdateVenueUseCase {

    private final VenueRepositoryPort repo;

    public UpdateVenueUseCaseImpl(VenueRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public Venue updateVenue(Long id, Venue venue) {
        Venue existing = repo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Venue with ID " + id + " not found."));

        if (!existing.getName().equalsIgnoreCase(venue.getName()) &&
            repo.existsByNameIgnoreCase(venue.getName())) {
            throw new IllegalArgumentException("Venue with name '" + venue.getName() + "' already exists.");
        }

        venue.setId(id);
        return repo.save(venue);
    }

}
