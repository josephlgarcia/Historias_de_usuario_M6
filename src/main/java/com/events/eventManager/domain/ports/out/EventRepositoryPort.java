package com.events.eventManager.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.events.eventManager.domain.model.Event;


public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> findById(Long Id);
    List<Event> findAll();
    Optional<Event> update(Event event);
    boolean deleteById(Long Id);
}
