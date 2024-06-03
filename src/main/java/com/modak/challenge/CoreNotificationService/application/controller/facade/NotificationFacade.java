package com.modak.challenge.CoreNotificationService.application.controller.facade;

import com.modak.challenge.CoreNotificationService.application.controller.dto.request.NotificationRequestDto;
import com.modak.challenge.CoreNotificationService.application.controller.dto.response.NotificationPagedResponseDto;
import com.modak.challenge.CoreNotificationService.application.controller.dto.response.NotificationResponseDto;
import com.modak.challenge.CoreNotificationService.application.controller.mapper.NotificationMapper;
import com.modak.challenge.CoreNotificationService.persistence.model.Notification;
import com.modak.challenge.CoreNotificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationFacade {
    @Autowired
    private final NotificationService service;

    @Autowired
    private final NotificationMapper mapper;

    public NotificationResponseDto send(NotificationRequestDto requestDto){
        return mapper.toDto(service.send(NotificationMapper.toEntity(requestDto)));
    }

    public NotificationPagedResponseDto getAllNotifications(PageRequest pageable) {
        Page<Notification> notifications = service.getAllNotifications(pageable);
        List<NotificationResponseDto> notificationDtos = notifications.getContent().stream()
                .map(notification -> new NotificationResponseDto(
                        notification.getId(),
                        notification.getType(),
                        notification.getUserId(),
                        notification.getMessage(),
                        notification.getTimestamp()))
                .collect(Collectors.toList());

        NotificationPagedResponseDto responseDto = new NotificationPagedResponseDto();
        responseDto.setTotalPages(notifications.getTotalPages());
        responseDto.setTotalElements(notifications.getTotalElements());
        responseDto.setSize(notifications.getSize());
        responseDto.setResponse(notificationDtos);
        return responseDto;
    }
}

