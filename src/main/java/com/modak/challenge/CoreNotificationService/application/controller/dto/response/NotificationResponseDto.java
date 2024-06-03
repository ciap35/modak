package com.modak.challenge.CoreNotificationService.application.controller.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDto {
    private Long id;
    private String type;
    private String userId;
    private String message;
    private LocalDateTime timestamp;
}
