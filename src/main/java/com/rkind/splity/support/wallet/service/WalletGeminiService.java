package com.rkind.splity.support.wallet.service;

import com.rkind.splity.support.wallet.dto.gemini.GeminiRequest;
import com.rkind.splity.support.wallet.dto.gemini.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class WalletGeminiService {

    private final RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    @Value("${gemini.url}")
    private String baseUrl;

    public WalletGeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String askGemini(String prompt) {

        GeminiRequest.Part part =
                new GeminiRequest.Part(prompt);

        GeminiRequest.Content content =
                new GeminiRequest.Content(
                        Collections.singletonList(part)
                );

        GeminiRequest request =
                new GeminiRequest(
                        Collections.singletonList(content)
                );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GeminiRequest> entity =
                new HttpEntity<>(request, headers);

        String url =
                baseUrl +
                        "/" +
                        model +
                        ":generateContent?key=" +
                        apiKey;        try {

            ResponseEntity<GeminiResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            GeminiResponse.class
                    );

            GeminiResponse body = response.getBody();

            if (body == null
                    || body.getCandidates() == null
                    || body.getCandidates().isEmpty()) {

                return "Sorry, I couldn't generate a response.";
            }

            GeminiResponse.Candidate candidate =
                    body.getCandidates().get(0);

            if (candidate.getContent() == null
                    || candidate.getContent().getParts() == null
                    || candidate.getContent().getParts().isEmpty()) {

                return "Sorry, I couldn't generate a response.";
            }

            return candidate
                    .getContent()
                    .getParts()
                    .get(0)
                    .getText();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String askGemini(String systemPrompt,
                            String conversationContext,
                            String userMessage) {

        String prompt = """
            %s

            %s

            Current User Message:
            %s

            Reply naturally as Splity AI Support Assistant.
            """
                .formatted(
                        systemPrompt,
                        conversationContext,
                        userMessage
                );

        return askGemini(prompt);
    }

    public String detectIntent(String message) {

        String prompt = """
            You are an Intent Detection AI.

            Your job is to identify only one intent.

            Possible intents:

            GREETING
            THANK_YOU
            WALLET_BALANCE
            PAYMENT_PENDING
            PAYMENT_FAILED
            TRANSACTION_HISTORY
            REFUND_STATUS
            TRANSFER_TO_AGENT
            UNKNOWN

            Return ONLY the intent name.

            User Message:
            %s
            """.formatted(message);

        return askGemini(prompt).trim();
    }

}