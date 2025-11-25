package com.events.eventManager.application.usecases.events;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.domain.ports.in.event.CreateEventUseCase;
import com.events.eventManager.domain.ports.out.EventRepositoryPort;
import com.events.eventManager.domain.ports.out.VenueRepositoryPort;

import jakarta.transaction.Transactional;

@Service
public class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventRepositoryPort eventRepo;
    private final VenueRepositoryPort venueRepo;

    public CreateEventUseCaseImpl(EventRepositoryPort eventRepo, VenueRepositoryPort venueRepo) {
        this.eventRepo = eventRepo;
        this.venueRepo = venueRepo;
    }

    @Transactional
    @Override
    public Event createEvent(Event event) {
        
        if (eventRepo.existsByNameIgnoreCase(event.getName())) {
            throw new IllegalArgumentException("Event with name '" + event.getName() + "' already exists.");
        }

        Venue venue = venueRepo.findById(event.getVenue().getId())
                            .orElseThrow(() -> new NoSuchElementException("Venue with ID " + event.getVenue().getId() + " not found."));
        
        event.setVenue(venue);

        return eventRepo.save(event);
    }

}
