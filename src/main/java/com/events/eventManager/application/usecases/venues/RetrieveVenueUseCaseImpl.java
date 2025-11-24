package com.events.eventManager.application.usecases.venues;

import java.util.List;
import java.util.Optional;

import com.events.eventManager.domain.model.Venue;
import com.events.eventManager.domain.ports.in.venue.RetrieveVenueUseCase;

public class RetrieveVenueUseCaseImpl implements RetrieveVenueUseCase {

    @Override
    public Optional<Venue> getVenueById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVenueById'");
    }

    @Override
    public List<Venue> getAllVenues() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllVenues'");
    }

}
