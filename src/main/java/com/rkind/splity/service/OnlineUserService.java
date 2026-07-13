package com.rkind.splity.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OnlineUserService {

    private final Set<Long> onlineUsers =
            ConcurrentHashMap.newKeySet();

    public void userConnected(Long userId) {

        if (userId != null) {
            onlineUsers.add(userId);
            System.out.println("🟢 User Online : " + userId);
        }

    }

    public void userDisconnected(Long userId) {

        if (userId != null) {
            onlineUsers.remove(userId);
            System.out.println("🔴 User Offline : " + userId);
        }

    }

    public boolean isOnline(Long userId) {

        return userId != null &&
                onlineUsers.contains(userId);

    }

    public int getOnlineCount() {

        return onlineUsers.size();

    }

    public Set<Long> getOnlineUsers() {

        return Collections.unmodifiableSet(onlineUsers);

    }

}