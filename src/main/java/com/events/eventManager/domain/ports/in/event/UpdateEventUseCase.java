package com.events.eventManager.domain.ports.in.event;

import com.events.eventManager.domain.model.Event;

public interface UpdateEventUseCase {
    Event updateEvent(Long id, Event event);
}
