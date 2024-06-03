package com.modak.challenge.CoreNotificationService.configuration.exception.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {
    @JsonProperty("error_description")
    private String errorDescription;
    @JsonProperty("error_code")
    private String errorCode;
}
