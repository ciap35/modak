package com.modak.challenge.CoreNotificationService.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateLimitConfig {
    public Map<String, RateLimit> rateLimits;
}