package com.events.eventManager.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.entity.EventEntity;
import com.events.eventManager.repository.EventRepository;
import com.events.eventManager.service.EventService;
import com.events.eventManager.web.dto.EventRequest;
import com.events.eventManager.web.dto.EventResponse;



@Service
public class EventServiceImpl implements EventService{

    private final EventRepository repo;


    public EventServiceImpl(EventRepository repo) {
        this.repo = repo;
    }

    
    @Transactional
    @Override
    public EventResponse create(EventRequest req) {

        if (repo.existsByNombreIgnoreCase(req.getName())) {
            throw new IllegalArgumentException("the event already exists");
        }

        EventEntity event = new EventEntity();
        event.setName(req.getName());
        event.setDate(req.getDate());
        event.setVenueId(req.getVenueId());

        var saved = repo.save(event);

        return new EventResponse(saved.getId(),saved.getName(), saved.getDate(), saved.getVenueId());
    }


    @Transactional
    @Override
    public EventResponse update(Long id, EventRequest req) {

        var event = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        event.setName(req.getName());
        event.setDate(req.getDate());
        event.setVenueId(req.getVenueId());

        var updated = repo.save(event);

        return new EventResponse(updated.getId(), updated.getName(), updated.getDate(), updated.getVenueId());
    }


    @Transactional(readOnly = true)
    @Override
    public EventResponse getById(Long id) {
        var event = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        return new EventResponse(event.getId(), event.getName(), event.getDate(), event.getVenueId());
    }


    @Transactional
    @Override
    public List<EventResponse> getAll() {
        return repo.findAll().stream()
                .map(event -> new EventResponse(event.getId(), event.getName(), event.getDate(), event.getVenueId()))
                .toList();
    }

}
