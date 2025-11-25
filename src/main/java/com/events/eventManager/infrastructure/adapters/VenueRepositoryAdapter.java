package com.events.eventManager.infrastructure.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.domain.ports.out.VenueRepositoryPort;
import com.events.eventManager.infrastructure.entities.VenueEntity;
import com.events.eventManager.infrastructure.mappers.VenueMapper;
import com.events.eventManager.infrastructure.repositories.JpaVenueRepository;

@Component
public class VenueRepositoryAdapter implements VenueRepositoryPort {

    private final JpaVenueRepository jpaRepository;
    private final VenueMapper venueMapper;

    public VenueRepositoryAdapter(JpaVenueRepository jpaRepository, VenueMapper venueMapper) {
        this.jpaRepository = jpaRepository;
        this.venueMapper = venueMapper;
    }

    @Override
    public Venue save(Venue venue) {
        VenueEntity entity = venueMapper.domainToEntity(venue);
        VenueEntity saved = jpaRepository.save(entity);
        return venueMapper.entityToDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venue> findById(Long id) {
        return jpaRepository.findById(id)
                .map(venueMapper::entityToDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venue> findAll() {
        return jpaRepository.findAll().stream()
                .map(venueMapper::entityToDomain)
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

}
