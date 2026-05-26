package com.nuthan.notificationservice.service;

import com.nuthan.notificationservice.model.Notification;
import com.nuthan.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Service
public class NotificationService {

    @Autowired private NotificationRepository repository;

    @Cacheable("notifications")
    public List<Notification> getAll() { return repository.findAll(); }

    public List<Notification> getByStatus(String status) { return repository.findByStatus(status); }
    public List<Notification> getByRecipient(String recipient) { return repository.findByRecipient(recipient); }

    @CacheEvict(value = "notifications", allEntries = true)
    public int processPendingNotifications() {
        List<Notification> pending = repository.findByStatus("PENDING");
        pending.forEach(n -> {
            n.setStatus("SENT");
            n.setSentAt(LocalDateTime.now());
            repository.save(n);
        });
        return pending.size();
    }

    public Map<String, Object> getSummary() {
        List<Notification> all = repository.findAll();
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("total", all.size());
        summary.put("sent", all.stream().filter(n -> "SENT".equals(n.getStatus())).count());
        summary.put("pending", all.stream().filter(n -> "PENDING".equals(n.getStatus())).count());
        Map<String, Long> byType = new LinkedHashMap<>();
        all.forEach(n -> byType.merge(n.getType(), 1L, Long::sum));
        summary.put("notificationsByType", byType);
        return summary;
    }
}