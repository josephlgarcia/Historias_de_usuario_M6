package com.events.eventManager.domain.ports.in.event;

import java.util.Optional;

import com.events.eventManager.domain.model.Event;

public interface CreateEventUseCase {
    Optional<Event> createEvent(Event event);
}
