package com.nuthan.notificationservice.controller;

import com.nuthan.notificationservice.model.Event;
import com.nuthan.notificationservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired private EventService service;

    @PostMapping
    public Event publish(@RequestBody Event event) { return service.publishEvent(event); }

    @GetMapping
    public List<Event> getAll() { return service.getAll(); }

    @GetMapping("/type")
    public List<Event> getByType(@RequestParam String name) { return service.getByType(name); }

    @GetMapping("/stats")
    public Map<String, Object> getStats() { return service.getStats(); }
}