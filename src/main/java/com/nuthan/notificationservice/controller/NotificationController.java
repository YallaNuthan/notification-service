package com.nuthan.notificationservice.controller;

import com.nuthan.notificationservice.service.NotificationService;
import com.nuthan.notificationservice.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired private NotificationService service;

    @GetMapping
    public List<Notification> getAll() { return service.getAll(); }

    @GetMapping("/status")
    public List<Notification> getByStatus(@RequestParam String value) { return service.getByStatus(value); }

    @GetMapping("/recipient")
    public List<Notification> getByRecipient(@RequestParam String email) { return service.getByRecipient(email); }

    @PostMapping("/process")
    public Map<String, Object> processPending() {
        int count = service.processPendingNotifications();
        return Map.of("processed", count, "message", count + " notifications sent successfully");
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() { return service.getSummary(); }
}