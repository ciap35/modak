package com.modak.challenge.CoreNotificationService.application.controller;


import com.modak.challenge.CoreNotificationService.application.controller.dto.request.NotificationRequestDto;
import com.modak.challenge.CoreNotificationService.application.controller.dto.response.NotificationPagedResponseDto;
import com.modak.challenge.CoreNotificationService.application.controller.dto.response.NotificationResponseDto;
import com.modak.challenge.CoreNotificationService.application.controller.facade.NotificationFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications/api/v1")
public class NotificationController {

    @Autowired
    private NotificationFacade facade;

    @PostMapping
    public ResponseEntity<?> sendNotification(@Valid @RequestBody  NotificationRequestDto requestDto) {
            NotificationResponseDto response = facade.send(requestDto);
            return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<NotificationPagedResponseDto> getAllNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        NotificationPagedResponseDto response = facade.getAllNotifications(PageRequest.of(page,size));
        return ResponseEntity.ok(response);
    }
}