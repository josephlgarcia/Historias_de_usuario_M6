package com.events.eventManager.infrastructure.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Events", description = "API for event management")
@RestController
@RequestMapping("/api/v1/event")
public class EventController {

}
