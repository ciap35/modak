package com.modak.challenge.CoreNotificationService.serviceImpl;

import com.modak.challenge.CoreNotificationService.configuration.RateLimit;
import com.modak.challenge.CoreNotificationService.configuration.RateLimitConfig;
import com.modak.challenge.CoreNotificationService.persistence.model.Notification;
import com.modak.challenge.CoreNotificationService.persistence.repository.NotificationRepository;
import com.modak.challenge.CoreNotificationService.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private RateLimitConfig rateLimitConfig;
    @Mock
    private RateLimit rateLimit;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    public void setUp() {
        rateLimitConfig = new RateLimitConfig();
        rateLimit = new RateLimit();
        rateLimit.setLimit(2);
        rateLimit.setDuration(1);
        rateLimit.setUnit("MINUTES");
        rateLimitConfig.setRateLimits(Map.of("status", rateLimit));
        notificationService = new NotificationServiceImpl(notificationRepository, rateLimitConfig);
    }

    @Test
    void saveStatusMessage_Success() {
        Notification notification = new Notification("status", "auth0|123", "Test message");

        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());

        Notification savedNotification = notificationService.send(notification);

        assertNotNull(savedNotification);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void save_StatusMessage_ExceedLimit() {
        Notification firstNotification = new Notification("status", "auth0|123", "Test message 1");
        Notification secondNotification = new Notification("status", "auth0|123", "Test message 2");
        Notification thirdNotification = new Notification("status", "auth0|123", "Test message 3");

        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(firstNotification);
        notificationList.add(secondNotification);

        when(notificationRepository.findByTypeAndUserIdAndTimestampAfter(firstNotification.getType(),firstNotification.getUserId(),firstNotification.getTimestamp())).thenReturn(notificationList);

        assertThrows(RuntimeException.class, () ->  notificationService.send(thirdNotification));
    }
}
