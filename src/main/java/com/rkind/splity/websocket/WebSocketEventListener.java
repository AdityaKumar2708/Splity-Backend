package com.rkind.splity.websocket;

import com.rkind.splity.service.OnlineUserService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final OnlineUserService onlineUserService;

    public WebSocketEventListener(
            OnlineUserService onlineUserService
    ) {
        this.onlineUserService = onlineUserService;
    }

    @EventListener
    public void handleWebSocketConnectListener(
            SessionConnectEvent event
    ) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(event.getMessage());

        String userIdHeader =
                accessor.getFirstNativeHeader("userId");

        if (userIdHeader != null) {

            try {

                Long userId = Long.parseLong(userIdHeader);

                onlineUserService.userConnected(userId);

                System.out.println(
                        "USER ONLINE : " + userId
                );

            } catch (Exception ignored) {
            }

        }

    }

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(event.getMessage());

        String userIdHeader =
                accessor.getFirstNativeHeader("userId");

        if (userIdHeader != null) {

            try {

                Long userId = Long.parseLong(userIdHeader);

                onlineUserService.userDisconnected(userId);

                System.out.println(
                        "USER OFFLINE : " + userId
                );

            } catch (Exception ignored) {
            }

        }

    }

}