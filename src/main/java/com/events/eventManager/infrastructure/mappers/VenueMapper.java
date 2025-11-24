package com.events.eventManager.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.infrastructure.entities.VenueEntity;
import com.events.eventManager.infrastructure.web.dto.venues.VenueRequest;
import com.events.eventManager.infrastructure.web.dto.venues.VenueResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VenueMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "capacity", source = "capacity")
    Venue entityToDomain(VenueEntity entity);

    @Mapping(target = "events", ignore = true)
    VenueEntity domainToEntity(Venue domain);

    VenueResponse domainToResponse(Venue domain);
    
    @Mapping(target = "id", ignore = true)
    Venue requestToDomain(VenueRequest request);

}
