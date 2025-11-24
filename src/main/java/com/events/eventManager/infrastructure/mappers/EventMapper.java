package com.events.eventManager.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.events.eventManager.domain.model.Event;
import com.events.eventManager.infrastructure.entities.EventEntity;
import com.events.eventManager.infrastructure.web.dto.events.EventRequest;
import com.events.eventManager.infrastructure.web.dto.events.EventResponse;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = VenueMapper.class  // Reutiliza VenueMapper para mapear venue
)
public interface EventMapper {

    Event entityToDomain(EventEntity entity);

    @Mapping(target = "venue", source = "venue")
    EventEntity domainToEntity(Event domain);

    EventResponse domainToResponse(Event domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venue", ignore = true)
    Event rquestToDomain(EventRequest request);
    
}
