package com.nuthan.notificationservice.scheduler;

import com.nuthan.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired private NotificationService service;

    @Scheduled(fixedRate = 60000)
    public void autoProcessPending() {
        int count = service.processPendingNotifications();
        if (count > 0) {
            System.out.println("[SCHEDULER] Auto-processed " + count + " pending notifications");
        }
    }
}