package com.events.eventManager.service;

import java.util.List;

import com.events.eventManager.web.dto.VenueRequest;
import com.events.eventManager.web.dto.VenueResponse;

public interface VenueService {

    VenueResponse create(VenueRequest req);
    VenueResponse update(Long id, VenueRequest req);
    VenueResponse getById(Long id);
    List<VenueResponse> getAll();
    void delete(Long id);

}
