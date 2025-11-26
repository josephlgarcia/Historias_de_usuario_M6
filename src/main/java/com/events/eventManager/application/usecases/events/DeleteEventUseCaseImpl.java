package com.events.eventManager.application.usecases.events;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.domain.ports.in.event.DeleteEventUseCase;
import com.events.eventManager.domain.ports.out.EventRepositoryPort;

@Service
public class DeleteEventUseCaseImpl implements DeleteEventUseCase {

    private final EventRepositoryPort repo;

    public DeleteEventUseCaseImpl(EventRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public void deleteEvent(Long id) {
        if (repo.existsById(id)) {
            throw new NoSuchElementException("Event with ID " + id + " not found.");
        }
        repo.deleteById(id);
    }

}
