package com.events.eventManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.events.eventManager.entity.VenueEntity;

public interface VenueRepository extends JpaRepository<VenueEntity, Long> {

    boolean existsByNameIgnoreCase(String name);

}
