package com.modak.challenge.CoreNotificationService.application.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDto {
    @NotBlank
    @NotNull
    private String type;
    @NotBlank
    @NotNull
    @JsonProperty("user_id")
    private String userId;
    @NotBlank
    @NotNull
    private String message;
}
