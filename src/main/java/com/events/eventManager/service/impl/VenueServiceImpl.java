package com.events.eventManager.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.eventManager.entity.VenueEntity;
import com.events.eventManager.repository.VenueRepository;
import com.events.eventManager.service.VenueService;
import com.events.eventManager.web.dto.VenueRequest;
import com.events.eventManager.web.dto.VenueResponse;



@Service
public class VenueServiceImpl implements VenueService{

    private final VenueRepository repo;

    public VenueServiceImpl(VenueRepository repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public VenueResponse create(VenueRequest req) {

        if (repo.existsByNameIgnoreCase(req.getName())) {
            throw new IllegalArgumentException("Venue with name '" + req.getName() + "' already exists.");
        }

        VenueEntity venue = new VenueEntity();
        venue.setName(req.getName());
        venue.setAddress(req.getAddress());
        venue.setCapacity(req.getCapacity());

        var saved = repo.save(venue);

        return new VenueResponse(saved.getId(), saved.getName(), saved.getAddress(), saved.getCapacity());
    }

    @Transactional
    @Override
    public VenueResponse update(Long id, VenueRequest req) {

        var venue = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Venue with ID " + id + " not found."));

        venue.setName(req.getName());
        venue.setAddress(req.getAddress());
        venue.setCapacity(req.getCapacity());

        var updated = repo.save(venue);

        return new VenueResponse(updated.getId(), updated.getName(), updated.getAddress(), updated.getCapacity());
    }

    @Transactional(readOnly = true)
    @Override
    public VenueResponse getById(Long id) {
        var venue = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Venue with ID " + id + " not found."));
        return new VenueResponse(venue.getId(), venue.getName(), venue.getAddress(), venue.getCapacity());
    }

    @Transactional(readOnly = true)
    @Override
    public List<VenueResponse> getAll() {
        return repo.findAll().stream()
                .map(venue -> new VenueResponse(venue.getId(), venue.getName(), venue.getAddress(), venue.getCapacity()))
                .toList();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Venue with ID " + id + " not found.");
        }
        repo.deleteById(id);
    }

}
