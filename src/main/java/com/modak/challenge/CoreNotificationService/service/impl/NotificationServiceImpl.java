package com.modak.challenge.CoreNotificationService.service.impl;

import com.modak.challenge.CoreNotificationService.configuration.RateLimit;
import com.modak.challenge.CoreNotificationService.configuration.RateLimitConfig;
import com.modak.challenge.CoreNotificationService.configuration.exception.custom.ExceedLimitException;
import com.modak.challenge.CoreNotificationService.persistence.model.Notification;
import com.modak.challenge.CoreNotificationService.persistence.repository.NotificationRepository;
import com.modak.challenge.CoreNotificationService.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final RateLimitConfig rateLimitConfig;


    public NotificationServiceImpl(NotificationRepository notificationRepository, RateLimitConfig rateLimitConfig) {
        this.notificationRepository = notificationRepository;
        this.rateLimitConfig = rateLimitConfig;
    }

    @Override
    public Notification send(Notification notification) {
        RateLimit rateLimit = rateLimitConfig.getRateLimits().get(notification.getType());
        if (rateLimit == null) {
            throw new RuntimeException("No rate limit configured for type: " + notification.getType());
        }

        int limit = rateLimit.getLimit();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minus(rateLimit.getDuration(), ChronoUnit.valueOf(rateLimit.getUnit().toUpperCase()));

        List<Notification> notifications = notificationRepository.findByTypeAndUserIdAndTimestampAfter(notification.getType(), notification.getUserId(), windowStart);


        if (notifications.size() >= limit) {
            throw new ExceedLimitException("CNS-001","Rate limit exceeded for type: " + notification.getType() + " and user: " + notification.getUserId());
        }


        int totalNotifications = notificationRepository.countAllByUserIdAndTimestampAfter(notification.getUserId(), LocalDate.now().atStartOfDay());

        if(rateLimit.getDailyLimit() != 0 && totalNotifications>= rateLimit.getDailyLimit()){
            throw new ExceedLimitException("CNS-002","Rate limit exceeded for type: " + notification.getType() + " and user: " + notification.getUserId());
        }


        Notification entity = new Notification(null, notification.getType(), notification.getUserId(), notification.getMessage(), now);
        return notificationRepository.save(entity);


    }

    @Override
    public Page<Notification> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }
}
