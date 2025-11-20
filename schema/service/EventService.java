package com.events.eventManager.service;

import java.util.List;

import com.events.eventManager.web.dto.EventRequest;
import com.events.eventManager.web.dto.EventResponse;

public interface EventService {

    EventResponse create(EventRequest req);
    EventResponse update(Long id, EventRequest req);
    EventResponse getById(Long id);
    List<EventResponse> getAll();
    void delete(Long id);

}
