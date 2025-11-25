package com.events.eventManager.infrastructure.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Venues", description = "API for venue management")
@RestController
@RequestMapping("/api/v1/venue")
public class VenueController {

}
