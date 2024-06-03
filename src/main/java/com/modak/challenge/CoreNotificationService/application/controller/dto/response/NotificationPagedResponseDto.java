package com.modak.challenge.CoreNotificationService.application.controller.dto.response;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationPagedResponseDto {
    private int totalPages;
    private long totalElements;
    private int size;
    private List<NotificationResponseDto> response;
}
