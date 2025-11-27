package com.events.eventManager.application.usecases.venues;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.domain.ports.in.venue.DeleteVenueUseCase;
import com.events.eventManager.domain.ports.out.VenueRepositoryPort;

@Service
public class DeleteVenueUseCaseImpl implements DeleteVenueUseCase {

    private final VenueRepositoryPort repo;

    public DeleteVenueUseCaseImpl(VenueRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public void deleteVenue(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Venue with ID " + id + " not found.");
        }
        repo.deleteById(id);
    }

}
