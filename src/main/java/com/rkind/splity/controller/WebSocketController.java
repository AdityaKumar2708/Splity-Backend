package com.rkind.splity.controller;

import com.rkind.splity.service.OnlineUserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final OnlineUserService onlineUserService;

    public WebSocketController(
            OnlineUserService onlineUserService
    ) {
        this.onlineUserService = onlineUserService;
    }

    @MessageMapping("/online")
    public void online(Long userId) {

        onlineUserService.userConnected(userId);

        System.out.println("🟢 User Online : " + userId);

    }

    @MessageMapping("/offline")
    public void offline(Long userId) {

        onlineUserService.userDisconnected(userId);

        System.out.println("🔴 User Offline : " + userId);

    }
}