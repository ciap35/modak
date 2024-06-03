package com.modak.challenge.CoreNotificationService.application.controller.mapper;

import com.modak.challenge.CoreNotificationService.application.controller.dto.request.NotificationRequestDto;
import com.modak.challenge.CoreNotificationService.application.controller.dto.response.NotificationResponseDto;
import com.modak.challenge.CoreNotificationService.persistence.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public static Notification toEntity(NotificationRequestDto dto){
        return Notification.builder()
                .type(dto.getType())
                .userId(dto.getUserId())
                .message(dto.getMessage())
                .build();
    }

    public NotificationResponseDto toDto(Notification entity){
        return NotificationResponseDto.builder()
                .id(entity.getId())
                .type(entity.getType())
                .userId(entity.getUserId())
                .message(entity.getMessage())
                .timestamp(entity.getTimestamp())
                .build();
    }

}
