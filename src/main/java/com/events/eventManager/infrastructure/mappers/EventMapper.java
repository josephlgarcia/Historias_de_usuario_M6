package com.events.eventManager.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.infrastructure.entities.EventEntity;
import com.events.eventManager.infrastructure.web.dto.events.EventRequest;
import com.events.eventManager.infrastructure.web.dto.events.EventResponse;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = VenueMapper.class  // Reutiliza VenueMapper para mapear venue
)
public interface EventMapper {

    Event entityToDomain(EventEntity event);

    @Mapping(target = "venue", source = "venue")
    EventEntity domainToEntity(Event event);

    EventResponse domainToResponse(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venue.id", source = "venueId")
    Event requestToDomain(EventRequest eventRequest);

}
