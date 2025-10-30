package com.events.eventManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.events.eventManager.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}
