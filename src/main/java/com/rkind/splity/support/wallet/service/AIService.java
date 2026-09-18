package com.rkind.splity.support.wallet.service;

import com.rkind.splity.support.wallet.dto.AIRequest;
import com.rkind.splity.support.wallet.dto.AIResponse;
import com.rkind.splity.support.wallet.enums.AIIntent;
import org.springframework.stereotype.Service;
import com.rkind.splity.support.wallet.context.ContextProvider;

import jakarta.annotation.PostConstruct;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final WalletGeminiService geminiService;
    private final ConversationMemoryService memoryService;
    private final List<ContextProvider> contextProviders;

    private final Map<AIIntent, ContextProvider> providerRegistry =
            new EnumMap<>(AIIntent.class);

    public AIService(
            WalletGeminiService geminiService,
            ConversationMemoryService memoryService,
            List<ContextProvider> contextProviders) {

        this.geminiService = geminiService;
        this.memoryService = memoryService;
        this.contextProviders = contextProviders;
    }

    private static final String SYSTEM_PROMPT = """
        You are Splity AI Support Assistant.

        Rules:

        - Always answer politely.
        - Never invent wallet balance.
        - Never invent payment status.
        - Never invent refund details.
        - Use only the information provided by the backend.
        - If information is unavailable, clearly say you don't have access.
        - Keep responses short and professional.
        - Remember the previous conversation while replying.
        """;

    @PostConstruct
    public void registerProviders() {

        for (ContextProvider provider : contextProviders) {

            for (AIIntent intent : provider.getSupportedIntents()) {

                if (providerRegistry.containsKey(intent)) {
                    throw new IllegalStateException(
                            "Duplicate ContextProvider for intent: " + intent
                    );
                }

                providerRegistry.put(intent, provider);
            }
        }
    }

    public AIResponse process(AIRequest request) {

        String detectedIntent = geminiService.detectIntent(request.getMessage());

        AIIntent intent = resolveIntent(
                geminiService.detectIntent(request.getMessage())
        );

        String conversationContext =
                memoryService.buildConversationContext(request.getUserId());

        ContextProvider provider = providerRegistry.get(intent);

        String backendContext =
                provider == null ? "" : provider.buildContext(request);

        switch (intent) {

            case GREETING:
            case THANK_YOU:
            case WALLET_BALANCE:
            case PAYMENT_PENDING:
            case PAYMENT_FAILED:
            case TRANSACTION_HISTORY:
            case REFUND_STATUS:

                return generateResponse(
                        request,
                        intent,
                        conversationContext,
                        backendContext
                );

            case TRANSFER_TO_AGENT:

                AIResponse response = generateResponse(
                        request,
                        intent,
                        conversationContext,
                        backendContext
                );

                response.setTransferToHuman(true);

                return response;

            default:

                return generateResponse(
                        request,
                        AIIntent.UNKNOWN,
                        conversationContext,
                        backendContext
                );
        }
    }

    private AIResponse success(String reply, AIIntent intent) {

        AIResponse response = new AIResponse();

        response.setSuccess(true);
        response.setReply(reply);
        response.setIntent(intent.name());
        response.setCreateTicket(false);
        response.setTransferToHuman(false);

        return response;
    }


    private AIResponse generateResponse(AIRequest request,
                                        AIIntent intent,
                                        String conversationContext,
                                        String backendContext) {

        String context = conversationContext;

        if (!backendContext.isBlank()) {
            context += "\n\n" + backendContext;
        }

        return success(
                geminiService.askGemini(
                        SYSTEM_PROMPT,
                        context,
                        request.getMessage()
                ),
                intent
        );
    }

    private AIIntent resolveIntent(String detectedIntent) {

        try {
            return AIIntent.valueOf(detectedIntent.trim().toUpperCase());
        } catch (Exception e) {
            return AIIntent.UNKNOWN;
        }
    }

}