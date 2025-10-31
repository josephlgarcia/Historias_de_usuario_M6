package com.events.eventManager.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.entity.EventEntity;
import com.events.eventManager.entity.VenueEntity;
import com.events.eventManager.repository.EventRepository;
import com.events.eventManager.repository.VenueRepository;
import com.events.eventManager.service.EventService;
import com.events.eventManager.web.dto.EventRequest;
import com.events.eventManager.web.dto.EventResponse;



@Service
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepo;
    private final VenueRepository venueRepo;


    public EventServiceImpl(EventRepository eventRepo, VenueRepository venueRepo) {
        this.eventRepo = eventRepo;
        this.venueRepo = venueRepo;
    }

    
    @Transactional
    @Override
    public EventResponse create(EventRequest req) {

        if (eventRepo.existsByNameIgnoreCase(req.getName())) {
            throw new IllegalArgumentException("Event with name '" + req.getName() + "' already exists.");

        }

        VenueEntity venue = venueRepo.findById(req.getVenueId())
                        .orElseThrow(() -> new NoSuchElementException("Venue with ID " + req.getVenueId() + " not found."));


        EventEntity event = new EventEntity();
        event.setName(req.getName());
        event.setDate(req.getDate());
        event.setVenue(venue);

        var saved = eventRepo.save(event);

        return new EventResponse(saved.getId(),saved.getName(), saved.getDate(), saved.getVenue().getId());
    }


    @Transactional
    @Override
    public EventResponse update(Long id, EventRequest req) {

        var event = eventRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event with ID " + id + " not found."));

        VenueEntity venue = venueRepo.findById(req.getVenueId())
                .orElseThrow(() -> new NoSuchElementException("Venue with ID " + req.getVenueId() + " not found."));

        event.setName(req.getName());
        event.setDate(req.getDate());
        event.setVenue(venue);

        var updated = eventRepo.save(event);

        return new EventResponse(updated.getId(), updated.getName(), updated.getDate(), updated.getVenue().getId());
    }


    @Transactional(readOnly = true)
    @Override
    public EventResponse getById(Long id) {
        var event = eventRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event with ID " + id + " not found."));
        return new EventResponse(event.getId(), event.getName(), event.getDate(), event.getVenue().getId());
    }


    @Transactional(readOnly = true)
    @Override
    public List<EventResponse> getAll() {
        return eventRepo.findAll().stream()
                .map(event -> new EventResponse(event.getId(), event.getName(), event.getDate(), event.getVenue().getId()))
                .toList();
    }

    
    @Transactional
    @Override
    public void delete(Long id) {
        if (!eventRepo.existsById(id)) {
            throw new NoSuchElementException("Event with ID " + id + " not found.");
        }
        eventRepo.deleteById(id);
    }

}
