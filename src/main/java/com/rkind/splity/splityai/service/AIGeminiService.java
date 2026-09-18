package com.rkind.splity.splityai.service;

import com.rkind.splity.splityai.config.GeminiConfig;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;
import com.rkind.splity.splityai.dto.response.ReceiptAnalysisResponse;
import com.rkind.splity.splityai.handler.GeminiHandler;
import com.rkind.splity.splityai.model.AIResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class AIGeminiService {

    private final RestTemplate restTemplate;
    private final GeminiConfig geminiConfig;
    private final ObjectMapper objectMapper;

    public AIGeminiService(
            RestTemplate restTemplate,
            GeminiConfig geminiConfig,
            ObjectMapper objectMapper
    ) {
        this.restTemplate = restTemplate;
        this.geminiConfig = geminiConfig;
        this.objectMapper = objectMapper;
    }

    public AIResponse generateResponse(String prompt) {

        try {

            long startTime = System.currentTimeMillis();

            String url =
                    geminiConfig.getUrl()
                            + geminiConfig.getModel()
                            + ":generateContent?key="
                            + geminiConfig.getApiKey();

            Map<String, Object> text = new HashMap<>();
            text.put("text", prompt);

            Map<String, Object> part = new HashMap<>();
            part.put("parts", Collections.singletonList(text));

            Map<String, Object> body = new HashMap<>();
            body.put("contents", Collections.singletonList(part));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            Map.class
                    );

            if (response.getBody() == null) {
                return AIResponse.builder()
                        .reply("No response received.")
                        .promptTokens(0)
                        .completionTokens(0)
                        .totalTokens(0)
                        .responseTime(0L)
                        .model(geminiConfig.getModel())
                        .build();
            }

            Map bodyMap = response.getBody();

            List candidates =
                    (List) bodyMap.get("candidates");

            if (candidates == null || candidates.isEmpty()) {
                return AIResponse.builder()
                        .reply("No AI response.")
                        .promptTokens(0)
                        .completionTokens(0)
                        .totalTokens(0)
                        .responseTime(0L)
                        .model(geminiConfig.getModel())
                        .build();
            }

            Map candidate = (Map) candidates.get(0);

            Map content =
                    (Map) candidate.get("content");

            List parts =
                    (List) content.get("parts");

            if (parts == null || parts.isEmpty()) {
                return AIResponse.builder()
                        .reply("No AI response.")
                        .promptTokens(0)
                        .completionTokens(0)
                        .totalTokens(0)
                        .responseTime(0L)
                        .model(geminiConfig.getModel())
                        .build();
            }

            Map firstPart = (Map) parts.get(0);

            String reply = String.valueOf(firstPart.get("text"));

            long endTime = System.currentTimeMillis();

            Integer promptTokens = 0;
            Integer completionTokens = 0;
            Integer totalTokens = 0;

            Map usage = (Map) bodyMap.get("usageMetadata");

            if (usage != null) {

                Object promptTokenCount = usage.get("promptTokenCount");
                Object completionTokenCount = usage.get("candidatesTokenCount");
                Object totalTokenCount = usage.get("totalTokenCount");

                promptTokens =
                        promptTokenCount == null ? 0 : ((Number) promptTokenCount).intValue();

                completionTokens =
                        completionTokenCount == null ? 0 : ((Number) completionTokenCount).intValue();

                totalTokens =
                        totalTokenCount == null ? 0 : ((Number) totalTokenCount).intValue();
            }

            return AIResponse.builder()
                    .reply(reply)
                    .promptTokens(promptTokens)
                    .completionTokens(completionTokens)
                    .totalTokens(totalTokens)
                    .responseTime(endTime - startTime)
                    .model(geminiConfig.getModel())
                    .build();

        } catch (HttpClientErrorException | HttpServerErrorException e) {

            e.printStackTrace();

            return AIResponse.builder()
                    .reply(e.getResponseBodyAsString())
                    .promptTokens(0)
                    .completionTokens(0)
                    .totalTokens(0)
                    .responseTime(0L)
                    .model(geminiConfig.getModel())
                    .build();
        }

        catch (Exception e) {

            e.printStackTrace();

            return AIResponse.builder()
                    .reply(e.toString())
                    .promptTokens(0)
                    .completionTokens(0)
                    .totalTokens(0)
                    .responseTime(0L)
                    .model(geminiConfig.getModel())
                    .build();
        }
    }

    public AIResponse generateImageResponse(
            String prompt,
            String base64Image,
            String mimeType
    ) {

        try {

            long startTime = System.currentTimeMillis();

            String url =
                    geminiConfig.getUrl()
                            + geminiConfig.getModel()
                            + ":generateContent?key="
                            + geminiConfig.getApiKey();

            Map<String, Object> textPart = new HashMap<>();
            textPart.put("text", prompt);

            Map<String, Object> inlineData = new HashMap<>();
            inlineData.put("mimeType", mimeType);
            inlineData.put("data", base64Image);

            Map<String, Object> imagePart = new HashMap<>();
            imagePart.put("inlineData", inlineData);

            List<Map<String, Object>> parts = new ArrayList<>();
            parts.add(textPart);
            parts.add(imagePart);

            Map<String, Object> content = new HashMap<>();
            content.put("parts", parts);

            Map<String, Object> body = new HashMap<>();
            body.put("contents", Collections.singletonList(content));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            Map.class
                    );

            if (response.getBody() == null) {
                return AIResponse.builder()
                        .reply("No response received.")
                        .model(geminiConfig.getModel())
                        .build();
            }

            Map bodyMap = response.getBody();

            List candidates =
                    (List) bodyMap.get("candidates");

            if (candidates == null || candidates.isEmpty()) {
                return AIResponse.builder()
                        .reply("No AI response.")
                        .model(geminiConfig.getModel())
                        .build();
            }

            Map candidate = (Map) candidates.get(0);

            Map contentMap =
                    (Map) candidate.get("content");

            List responseParts =
                    (List) contentMap.get("parts");

            Map firstPart =
                    (Map) responseParts.get(0);

            String reply =
                    String.valueOf(firstPart.get("text"));

            long endTime = System.currentTimeMillis();

            Integer promptTokens = 0;
            Integer completionTokens = 0;
            Integer totalTokens = 0;

            Map usage =
                    (Map) bodyMap.get("usageMetadata");

            if (usage != null) {

                promptTokens =
                        usage.get("promptTokenCount") == null
                                ? 0
                                : ((Number) usage.get("promptTokenCount")).intValue();

                completionTokens =
                        usage.get("candidatesTokenCount") == null
                                ? 0
                                : ((Number) usage.get("candidatesTokenCount")).intValue();

                totalTokens =
                        usage.get("totalTokenCount") == null
                                ? 0
                                : ((Number) usage.get("totalTokenCount")).intValue();
            }

            return AIResponse.builder()
                    .reply(reply)
                    .promptTokens(promptTokens)
                    .completionTokens(completionTokens)
                    .totalTokens(totalTokens)
                    .responseTime(endTime - startTime)
                    .model(geminiConfig.getModel())
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return AIResponse.builder()
                    .reply("Unable to analyze image.")
                    .model(geminiConfig.getModel())
                    .build();
        }
    }

    public ReceiptAnalysisResponse parseReceipt(String json) {

        try {
            return objectMapper.readValue(
                    json,
                    ReceiptAnalysisResponse.class
            );
        } catch (Exception e) {
            return null;
        }
    }

}