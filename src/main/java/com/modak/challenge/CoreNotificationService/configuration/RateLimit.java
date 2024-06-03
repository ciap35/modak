package com.modak.challenge.CoreNotificationService.configuration;

import lombok.Data;

@Data
public class RateLimit {
    private int limit;
    private int duration;
    private String unit;
}