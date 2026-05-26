package com.nuthan.notificationservice.service;

import com.nuthan.notificationservice.model.Event;
import com.nuthan.notificationservice.model.Notification;
import com.nuthan.notificationservice.repository.EventRepository;
import com.nuthan.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Service
public class EventService {

    @Autowired private EventRepository eventRepository;
    @Autowired private NotificationRepository notificationRepository;

    public Event publishEvent(Event event) {
        event.setStatus("PENDING");
        Event saved = eventRepository.save(event);
        processEvent(saved);
        return saved;
    }

    private void processEvent(Event event) {
        Notification notification = new Notification();
        notification.setRecipient("system@autorabit.com");
        notification.setType(event.getEventType());
        notification.setStatus("PENDING");

        switch (event.getEventType()) {
            case "DEPLOYMENT" -> notification.setMessage("New deployment event triggered: " + event.getPayload());
            case "BUILD_FAILURE" -> notification.setMessage("Build failed: " + event.getPayload());
            case "CODE_REVIEW" -> notification.setMessage("Code review requested: " + event.getPayload());
            case "RELEASE" -> notification.setMessage("Release initiated: " + event.getPayload());
            default -> notification.setMessage("Event received: " + event.getPayload());
        }

        notificationRepository.save(notification);
        event.setStatus("PROCESSED");
        eventRepository.save(event);
    }

    public List<Event> getAll() { return eventRepository.findAll(); }
    public List<Event> getByStatus(String status) { return eventRepository.findByStatus(status); }
    public List<Event> getByType(String type) { return eventRepository.findByEventType(type); }

    public Map<String, Object> getStats() {
        List<Event> all = eventRepository.findAll();
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalEvents", all.size());
        stats.put("processed", all.stream().filter(e -> "PROCESSED".equals(e.getStatus())).count());
        stats.put("pending", all.stream().filter(e -> "PENDING".equals(e.getStatus())).count());
        Map<String, Long> byType = new LinkedHashMap<>();
        all.forEach(e -> byType.merge(e.getEventType(), 1L, Long::sum));
        stats.put("eventsByType", byType);
        return stats;
    }
}