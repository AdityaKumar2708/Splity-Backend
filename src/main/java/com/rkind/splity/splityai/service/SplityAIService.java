package com.rkind.splity.splityai.service;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;
import com.rkind.splity.splityai.dto.response.DashboardStartResponse;
import com.rkind.splity.splityai.entity.AIConversation;
import com.rkind.splity.splityai.enums.SplityIntent;
import com.rkind.splity.splityai.handler.GeminiHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SplityAIService {

    private final AIFunctionRouter aiFunctionRouter;
    private final AIIntentDetectionService aiIntentDetectionService;
    private final AIConversationPersistenceService conversationPersistenceService;
    private final GeminiHandler geminiHandler;

    public SplityAIService(
            AIFunctionRouter aiFunctionRouter,
            AIIntentDetectionService aiIntentDetectionService,
            AIConversationPersistenceService conversationPersistenceService,
            GeminiHandler geminiHandler

    ) {

        this.aiFunctionRouter = aiFunctionRouter;
        this.aiIntentDetectionService = aiIntentDetectionService;
        this.conversationPersistenceService = conversationPersistenceService;
        this.geminiHandler = geminiHandler;
    }

    public DashboardStartResponse startChat(Long userId) {

        AIConversation conversation =
                conversationPersistenceService.createConversation(userId);

        DashboardStartResponse response =
                new DashboardStartResponse();

        response.setSuccess(true);

        response.setConversationId(
                conversation.getConversationId()
        );

        response.setAiName("Splity AI");

        response.setAiVersion("1.0");

        response.setAiReady(true);

        response.setWelcomeMessage(
                """
                Hello! I'm Splity AI 👋
    
                I can help you with:
    
                • Wallet
                • Split Expenses
                • Travel
                • Groups
                • App Navigation
                • General Questions
                • Programming
                • Education
    
                How can I help you today?
                """
        );

        response.setSuggestions(
                java.util.Arrays.asList(
                        "Open Wallet",
                        "Split Expenses",
                        "Travel",
                        "Help"
                )
        );

        return response;
    }

    public DashboardChatResponse sendMessage(DashboardChatRequest request) {

        DashboardChatResponse response = new DashboardChatResponse();

        try {

            if (request == null ||
                    request.getUserId() == null ||
                    request.getMessage() == null ||
                    request.getMessage().trim().isEmpty()) {

                response.setSuccess(false);
                response.setReply("Message cannot be empty.");
                return response;
            }

            SplityIntent intent =
                    aiIntentDetectionService.detectIntent(request);

            return aiFunctionRouter.route(intent, request);

        } catch (Exception e) {

            e.printStackTrace();

            response.setSuccess(false);
            response.setReply("Something went wrong while processing your request.");

            return response;
        }
    }

    public DashboardChatResponse processImage(
            Long userId,
            String conversationId,
            MultipartFile image,
            String message
    ) {

        DashboardChatResponse response = new DashboardChatResponse();

        response.setSuccess(true);
        response.setReply("Image received successfully.");

        return geminiHandler.processImage(
                userId,
                conversationId,
                image,
                message
        );
    }
}