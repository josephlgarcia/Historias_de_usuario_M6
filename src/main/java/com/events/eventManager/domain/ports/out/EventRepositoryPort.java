package com.events.eventManager.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.events.eventManager.domain.model.Event;


public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> findById(Long id);
    List<Event> findAll();
    void deleteById(Long id);
    boolean existsByNameIgnoreCase(String name);
}
