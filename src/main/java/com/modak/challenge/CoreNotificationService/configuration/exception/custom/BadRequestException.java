package com.modak.challenge.CoreNotificationService.configuration.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BadRequestException extends RuntimeException{
    private final String errorCode;
    private final String errorDescription;
}
