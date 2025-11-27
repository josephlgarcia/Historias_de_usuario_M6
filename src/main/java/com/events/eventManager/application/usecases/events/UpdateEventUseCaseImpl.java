package com.events.eventManager.application.usecases.events;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.domain.ports.in.event.UpdateEventUseCase;
import com.events.eventManager.domain.ports.out.EventRepositoryPort;
import com.events.eventManager.domain.ports.out.VenueRepositoryPort;

@Service
public class UpdateEventUseCaseImpl implements UpdateEventUseCase {

    private final EventRepositoryPort eventRepo;
    private final VenueRepositoryPort venueRepo;

    public UpdateEventUseCaseImpl(EventRepositoryPort eventRepo, VenueRepositoryPort venueRepo) {
        this.eventRepo = eventRepo;
        this.venueRepo = venueRepo;
    }

    @Transactional
    @Override
    public Event updateEvent(Long id, Event event) {
        Event existing = eventRepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Event with ID " + id + " not found."));
        
        if (!existing.getName().equalsIgnoreCase(event.getName()) &&
            eventRepo.existsByNameIgnoreCase(event.getName())) {
            throw new IllegalArgumentException("Event with name '" + event.getName() + "' already exists.");
        }
        event.setId(id);

        Venue venue = venueRepo.findById(event.getVenue().getId())
                            .orElseThrow(() -> new NoSuchElementException("Venue with ID " + event.getVenue().getId() + " not found."));

        event.setVenue(venue);
        
        return eventRepo.save(event);
    }

}

