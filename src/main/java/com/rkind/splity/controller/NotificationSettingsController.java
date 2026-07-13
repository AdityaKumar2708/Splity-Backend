package com.rkind.splity.controller;

import com.rkind.splity.dto.notification.NotificationSettingsRequestDto;
import com.rkind.splity.dto.notification.NotificationSettingsResponseDto;
import com.rkind.splity.service.NotificationSettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification-settings")
public class NotificationSettingsController {

    private final NotificationSettingsService service;

    public NotificationSettingsController(
            NotificationSettingsService service
    ) {
        this.service = service;
    }

    /*
     * Load Notification Settings
     */
    @GetMapping("/{userId}")
    public ResponseEntity<NotificationSettingsResponseDto> getSettings(
            @PathVariable Long userId
    ) {

        System.out.println("GET USER = " + userId);

        return ResponseEntity.ok(
                service.getSettings(userId)
        );
    }

    /*
     * Save / Update Notification Settings
     */
    @PutMapping
    public ResponseEntity<NotificationSettingsResponseDto> saveSettings(
            @RequestBody NotificationSettingsRequestDto request
    ) {
        System.out.println("SAVE USER = " + request.getUserId());
        System.out.println("SAVE MESSAGE = " + request.isMessageNotification());

        return ResponseEntity.ok(service.saveSettings(request));
    }
    @GetMapping("/sounds")
    public ResponseEntity<java.util.List<com.rkind.splity.dto.notification.NotificationSoundDto>> getNotificationSounds() {

        return ResponseEntity.ok(
                service.getNotificationSounds()
        );

    }


    @PostMapping("/reset/{userId}")
    public ResponseEntity<NotificationSettingsResponseDto> resetSettings(
            @PathVariable Long userId
    ){
        return ResponseEntity.ok(service.resetSettings(userId));
    }

}