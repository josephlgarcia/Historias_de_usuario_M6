package com.events.eventManager.application.usecases.venues;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.domain.ports.in.venue.RetrieveVenueUseCase;
import com.events.eventManager.domain.ports.out.VenueRepositoryPort;

@Service
public class RetrieveVenueUseCaseImpl implements RetrieveVenueUseCase {

    private final VenueRepositoryPort repo;

    public RetrieveVenueUseCaseImpl(VenueRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Venue> getVenueById(Long id) {
        Venue venue = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Venue with ID " + id + " not found."));
        return Optional.of(venue);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Venue> getAllVenues() {
        return repo.findAll();
    }

}
