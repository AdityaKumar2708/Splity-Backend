package com.rkind.splity.controller;

import com.rkind.splity.service.OnlineUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-status")
public class UserStatusController {

    private final OnlineUserService onlineUserService;

    public UserStatusController(
            OnlineUserService onlineUserService
    ) {
        this.onlineUserService = onlineUserService;
    }

    @PostMapping("/online/{userId}")
    public ResponseEntity<Void> userOnline(
            @PathVariable Long userId
    ) {

        onlineUserService.userConnected(userId);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/offline/{userId}")
    public ResponseEntity<Void> userOffline(
            @PathVariable Long userId
    ) {

        onlineUserService.userDisconnected(userId);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/is-online/{userId}")
    public ResponseEntity<Boolean> isOnline(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                onlineUserService.isOnline(userId)
        );

    }
}