package com.events.eventManager.infrastructure.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.domain.ports.out.EventRepositoryPort;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class EventRepositoryAdapter implements EventRepositoryPort{

    @Override
    public Event save(Event event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Optional<Event> findById(Long Id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Event> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Optional<Event> update(Event event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean deleteById(Long Id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
