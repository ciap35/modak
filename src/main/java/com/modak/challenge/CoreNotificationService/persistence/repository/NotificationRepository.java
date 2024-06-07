package com.modak.challenge.CoreNotificationService.persistence.repository;

import com.modak.challenge.CoreNotificationService.persistence.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTypeAndUserIdAndTimestampAfter(String type, String userId, LocalDateTime timestamp);
    int countAllByUserIdAndTimestampAfter(String userId,LocalDateTime localDateTime);

}

