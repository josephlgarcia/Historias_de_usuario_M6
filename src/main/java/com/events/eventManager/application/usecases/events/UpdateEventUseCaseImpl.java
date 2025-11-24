package com.events.eventManager.application.usecases.events;

import java.util.Optional;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.domain.ports.in.event.UpdateEventUseCase;

public class UpdateEventUseCaseImpl implements UpdateEventUseCase {

    @Override
    public Optional<Event> updateEvent(Long id, Event event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateEvent'");
    }

}

