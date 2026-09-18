package com.rkind.splity.splityai.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AIConversationMemoryService {

    private static final int MAX_HISTORY = 10;

    private final Map<Long, LinkedList<String>> conversations =
            new ConcurrentHashMap<>();

    public void saveUserMessage(Long userId, String message) {

        LinkedList<String> history =
                conversations.computeIfAbsent(
                        userId,
                        k -> new LinkedList<>()
                );

        history.add("User : " + message);

        trimHistory(history);
    }

    public void saveAIResponse(Long userId, String response) {

        LinkedList<String> history =
                conversations.computeIfAbsent(
                        userId,
                        k -> new LinkedList<>()
                );

        history.add("Splity AI : " + response);

        trimHistory(history);
    }

    public String getConversation(Long userId) {

        LinkedList<String> history =
                conversations.get(userId);

        if (history == null || history.isEmpty()) {
            return "";
        }

        return String.join("\n", history);
    }

    public void clearConversation(Long userId) {
        conversations.remove(userId);
    }

    private void trimHistory(LinkedList<String> history) {

        while (history.size() > MAX_HISTORY) {
            history.removeFirst();
        }

    }

}