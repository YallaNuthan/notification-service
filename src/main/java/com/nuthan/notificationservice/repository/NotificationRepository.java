package com.nuthan.notificationservice.repository;

import com.nuthan.notificationservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatus(String status);
    List<Notification> findByRecipient(String recipient);
    List<Notification> findByType(String type);
}