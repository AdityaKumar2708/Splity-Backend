package com.rkind.splity.splityai.controller;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;
import com.rkind.splity.splityai.dto.response.DashboardStartResponse;
import com.rkind.splity.splityai.service.SplityAIService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/splity-ai")
public class SplityAIController {

    private final SplityAIService splityAIService;

    public SplityAIController(SplityAIService splityAIService) {
        this.splityAIService = splityAIService;
    }

    @PostMapping("/start")
    public DashboardStartResponse startChat(
            @RequestParam Long userId
    ) {
        return splityAIService.startChat(userId);
    }

    @PostMapping("/chat")
    public DashboardChatResponse sendMessage(
            @RequestBody DashboardChatRequest request
    ) {
        return splityAIService.sendMessage(request);
    }

    @PostMapping(
            value = "/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public DashboardChatResponse processImage(

            @RequestParam Long userId,

            @RequestParam(required = false)
            String conversationId,

            @RequestParam(required = false)
            String message,

            @RequestPart MultipartFile image
    ) {

        return splityAIService.processImage(
                userId,
                conversationId,
                image,
                message
        );
    }


}