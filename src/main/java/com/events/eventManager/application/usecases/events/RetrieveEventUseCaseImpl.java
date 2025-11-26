
package com.events.eventManager.application.usecases.events;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.domain.ports.in.event.RetrieveEventUseCase;
import com.events.eventManager.domain.ports.out.EventRepositoryPort;


@Service
public class RetrieveEventUseCaseImpl implements RetrieveEventUseCase {

    private final EventRepositoryPort repo;

    public RetrieveEventUseCaseImpl(EventRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Event> getEventById(Long id) {
        Event event = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event with ID " + id + " not found."));
        return Optional.of(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Event> getAllEvents() {
        return repo.findAll();
    }

}
