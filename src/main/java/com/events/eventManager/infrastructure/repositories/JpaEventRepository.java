package com.events.eventManager.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.events.eventManager.infrastructure.entities.EventEntity;

@Repository
public interface JpaEventRepository extends JpaRepository<EventEntity, Long>{
    boolean existsByNameIgnoreCase(String name);
}