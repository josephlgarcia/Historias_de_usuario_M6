package com.events.eventManager.infrastructure.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.events.eventManager.infrastructure.entities.EventEntity;

@Repository
public interface JpaEventRepository extends JpaRepository<EventEntity, Long>{
    boolean existsByNameIgnoreCase(String name);

    @EntityGraph(value = "Event.withVenue", type = EntityGraph.EntityGraphType.LOAD)
    List<EventEntity> findByVenue_CapacityGreaterThanEqual(@Param("capacity") Integer capacity);
}