package com.rkind.splity.support.wallet.service;

import com.rkind.splity.support.wallet.entity.ChatConversation;
import com.rkind.splity.support.wallet.repository.ChatConversationRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ConversationMemoryService {

    private final ChatConversationRepository repository;

    public ConversationMemoryService(ChatConversationRepository repository) {
        this.repository = repository;
    }

    public void saveUserMessage(Long userId,
                                String ticketNumber,
                                String message) {

        ChatConversation conversation = new ChatConversation();

        conversation.setUserId(userId);
        conversation.setTicketNumber(ticketNumber);
        conversation.setRole("USER");
        conversation.setMessage(message);

        repository.save(conversation);
    }

    public void saveAIMessage(Long userId,
                              String ticketNumber,
                              String message) {

        ChatConversation conversation = new ChatConversation();

        conversation.setUserId(userId);
        conversation.setTicketNumber(ticketNumber);
        conversation.setRole("AI");
        conversation.setMessage(message);

        repository.save(conversation);
    }

    public List<ChatConversation> getRecentConversation(Long userId) {

        List<ChatConversation> list =
                repository.findTop10ByUserIdOrderByCreatedAtDesc(userId);

        Collections.reverse(list);

        return list;
    }

    public String buildConversationContext(Long userId) {

        List<ChatConversation> conversations =
                getRecentConversation(userId);

        StringBuilder context = new StringBuilder();

        context.append("Previous Conversation:\n\n");

        for (ChatConversation conversation : conversations) {

            context.append(conversation.getRole())
                    .append(": ")
                    .append(conversation.getMessage())
                    .append("\n");
        }

        return context.toString();
    }

}