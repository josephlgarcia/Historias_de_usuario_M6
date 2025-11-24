package com.events.eventManager.domain.ports.in.event;

import java.util.List;
import java.util.Optional;

import com.events.eventManager.domain.model.Event;

public interface RetrieveEventUseCase {
    Optional<Event> getEventById(Long id);
    List<Event> getAllEvents();
}
