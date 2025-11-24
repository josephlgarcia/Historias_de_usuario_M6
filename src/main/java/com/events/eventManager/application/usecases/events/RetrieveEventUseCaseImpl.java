
package com.events.eventManager.application.usecases.events;

import java.util.List;
import java.util.Optional;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.domain.ports.in.event.RetrieveEventUseCase;

public class RetrieveEventUseCaseImpl implements RetrieveEventUseCase {

    @Override
    public Optional<Event> getEventById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEventById'");
    }

    @Override
    public List<Event> getAllEvents() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEvents'");
    }

}
