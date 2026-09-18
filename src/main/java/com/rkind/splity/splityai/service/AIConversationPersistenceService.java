package com.rkind.splity.splityai.service;

import com.rkind.splity.splityai.entity.AIConversation;
import com.rkind.splity.splityai.entity.AIConversationMessage;
import com.rkind.splity.splityai.entity.AIConversationSummary;
import com.rkind.splity.splityai.entity.AIFeedback;
import com.rkind.splity.splityai.repository.AIConversationMessageRepository;
import com.rkind.splity.splityai.repository.AIConversationRepository;
import com.rkind.splity.splityai.repository.AIConversationSummaryRepository;
import com.rkind.splity.splityai.repository.AIFeedbackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AIConversationPersistenceService {

    private final AIConversationRepository conversationRepository;
    private final AIConversationMessageRepository messageRepository;
    private final AIFeedbackRepository feedbackRepository;
    private final AIConversationSummaryRepository summaryRepository;

    public AIConversationPersistenceService(
            AIConversationRepository conversationRepository,
            AIConversationMessageRepository messageRepository,
            AIFeedbackRepository feedbackRepository,
            AIConversationSummaryRepository summaryRepository
    ) {

        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.feedbackRepository = feedbackRepository;
        this.summaryRepository = summaryRepository;
    }

    public AIConversation createConversation(Long userId) {

        AIConversation conversation = AIConversation.builder()
                .conversationId(UUID.randomUUID().toString())
                .userId(userId)
                .title("New Conversation")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return conversationRepository.save(conversation);

    }

    public AIConversation getConversation(String conversationId) {

        return conversationRepository
                .findByConversationId(conversationId)
                .orElse(null);

    }

    public AIConversationMessage saveUserMessage(
            String conversationId,
            Long userId,
            String message,
            String intent
    ) {

        AIConversationMessage chatMessage =
                AIConversationMessage.builder()
                        .conversationId(conversationId)
                        .userId(userId)
                        .role("USER")
                        .message(message)
                        .messageType("TEXT")
                        .detectedIntent(intent)
                        .createdAt(LocalDateTime.now())
                        .build();

        return messageRepository.save(chatMessage);

    }

    public AIConversationMessage saveAIMessage(
            String conversationId,
            Long userId,
            String message,
            String intent,
            Integer promptTokens,
            Integer completionTokens,
            Integer totalTokens,
            Long responseTime,
            String modelName,
            Boolean fromCache,
            Double confidenceScore,
            String reason
    ) {

        AIConversationMessage aiMessage =
                AIConversationMessage.builder()
                        .conversationId(conversationId)
                        .userId(userId)
                        .role("AI")
                        .message(message)
                        .messageType("TEXT")
                        .detectedIntent(intent)
                        .promptTokens(promptTokens)
                        .completionTokens(completionTokens)
                        .totalTokens(totalTokens)
                        .responseTime(responseTime)
                        .modelName(modelName)
                        .fromCache(false)
                        .createdAt(LocalDateTime.now())
                        .build();


        return messageRepository.save(aiMessage);

    }

    public List<AIConversationMessage> getConversationMessages(
            String conversationId
    ) {

        return messageRepository
                .findByConversationIdOrderByCreatedAtAsc(conversationId);

    }

    public AIFeedback saveFeedback(
            String conversationId,
            Long messageId,
            Long userId,
            Boolean helpful,
            String feedback
    ) {

        AIFeedback aiFeedback =
                AIFeedback.builder()
                        .conversationId(conversationId)
                        .messageId(messageId)
                        .userId(userId)
                        .helpful(helpful)
                        .feedback(feedback)
                        .createdAt(LocalDateTime.now())
                        .build();

        return feedbackRepository.save(aiFeedback);

    }

    public AIConversationSummary updateSummary(
            String conversationId,
            Long userId,
            String summary,
            Integer totalMessages
    ) {

        AIConversationSummary conversationSummary =
                summaryRepository
                        .findByConversationId(conversationId)
                        .orElse(new AIConversationSummary());

        conversationSummary.setConversationId(conversationId);
        conversationSummary.setUserId(userId);
        conversationSummary.setSummary(summary);
        conversationSummary.setTotalMessages(totalMessages);
        conversationSummary.setLastMessageAt(LocalDateTime.now());
        conversationSummary.setUpdatedAt(LocalDateTime.now());

        return summaryRepository.save(conversationSummary);

    }

}