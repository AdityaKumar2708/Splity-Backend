package com.rkind.splity.controller;

import com.rkind.splity.dto.CreateSessionRequest;
import com.rkind.splity.entity.UserSession;
import com.rkind.splity.service.UserSessionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class UserSessionController {

    private final UserSessionService userSessionService;

    public UserSessionController(
            UserSessionService userSessionService
    ) {
        this.userSessionService = userSessionService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createSession(
            @RequestBody CreateSessionRequest request
    ) {

        try {

            if (request.getUserId() == null
                    || request.getUserId() <= 0) {

                return ResponseEntity.badRequest()
                        .body("Invalid userId");
            }


            if (request.getDeviceId() == null
                    || request.getDeviceId().trim().isEmpty()) {

                return ResponseEntity.badRequest()
                        .body("Device ID is required");
            }


            if (request.getDeviceType() == null
                    || request.getDeviceType().trim().isEmpty()) {

                return ResponseEntity.badRequest()
                        .body("Device type is required");
            }


            if (request.getPlatform() == null
                    || request.getPlatform().trim().isEmpty()) {

                return ResponseEntity.badRequest()
                        .body("Platform is required");
            }


            UserSession session =
                    userSessionService.createSession(
                            request.getUserId(),
                            request.getDeviceId(),
                            request.getDeviceType(),
                            request.getPlatform(),
                            request.getFcmToken()
                    );


            return ResponseEntity.ok(session);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/fcm-token")
    public ResponseEntity<?> updateFcmToken(
            @RequestParam Long userId,
            @RequestParam String deviceId,
            @RequestParam String fcmToken
    ) {

        try {

            UserSession session =
                    userSessionService.updateFcmToken(
                            userId,
                            deviceId,
                            fcmToken
                    );

            return ResponseEntity.ok(session);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/active")
    public ResponseEntity<?> updateLastActive(
            @RequestParam Long userId,
            @RequestParam String deviceId
    ) {

        try {

            UserSession session =
                    userSessionService.updateLastActive(
                            userId,
                            deviceId
                    );

            return ResponseEntity.ok(session);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/deactivate")
    public ResponseEntity<?> deactivateSession(
            @RequestParam Long userId,
            @RequestParam String deviceId
    ) {

        try {

            UserSession session =
                    userSessionService.deactivateSession(
                            userId,
                            deviceId
                    );

            return ResponseEntity.ok(session);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}