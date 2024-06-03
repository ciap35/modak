package com.modak.challenge.CoreNotificationService.service;

import com.modak.challenge.CoreNotificationService.persistence.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    Notification send(Notification notification);
    Page<Notification> getAllNotifications(Pageable pageable);
}