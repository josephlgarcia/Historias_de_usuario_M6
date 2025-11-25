package com.events.eventManager.domain.ports.in.event;


import com.events.eventManager.domain.model.Event;

public interface CreateEventUseCase {
    Event createEvent(Event event);
}
