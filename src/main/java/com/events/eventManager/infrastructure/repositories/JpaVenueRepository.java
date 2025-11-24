package com.events.eventManager.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.events.eventManager.infrastructure.entities.VenueEntity;

@Repository
public interface JpaVenueRepository extends JpaRepository<VenueEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}
