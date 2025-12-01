package com.events.eventManager.infrastructure.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.domain.ports.out.EventRepositoryPort;
import com.events.eventManager.infrastructure.entities.EventEntity;
import com.events.eventManager.infrastructure.mappers.EventMapper;
import com.events.eventManager.infrastructure.repositories.JpaEventRepository;

@Component
public class EventRepositoryAdapter implements EventRepositoryPort{

    private final JpaEventRepository jpaRepository;
    private final EventMapper eventMapper;

    public EventRepositoryAdapter(JpaEventRepository jpaRepository, EventMapper eventMapper) {
        this.jpaRepository = jpaRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public Event save(Event event) {
        EventEntity entity = eventMapper.domainToEntity(event);
        EventEntity saved = jpaRepository.save(entity);
        return eventMapper.entityToDomain(saved);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return jpaRepository.findById(id)
                .map(eventMapper::entityToDomain);
    }

    @Override
    public List<Event> findAll() {
        return jpaRepository.findAll().stream()
                .map(eventMapper::entityToDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return jpaRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Event> findByVenue_CapacityGreaterThanEqual(Integer capacity) {
        return jpaRepository.findByVenue_CapacityGreaterThanEqual(capacity).stream()
                .map(eventMapper::entityToDomain)
                .toList();
    }

}
