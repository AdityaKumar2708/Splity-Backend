package com.rkind.splity.splityai.controller;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.dto.request.DashboardStartRequest;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;
import com.rkind.splity.splityai.dto.response.DashboardStartResponse;
import com.rkind.splity.splityai.service.SplityAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class DashboardAIController {

    private final SplityAIService splityAIService;

    @PostMapping("/start")
    public ResponseEntity<DashboardStartResponse> start(
            @RequestBody DashboardStartRequest request
    ) {

        return ResponseEntity.ok(
                splityAIService.startChat(
                        request.getUserId()
                )
        );
    }

    @PostMapping("/chat")
    public ResponseEntity<DashboardChatResponse> chat(
            @RequestBody DashboardChatRequest request
    ) {

        return ResponseEntity.ok(
                splityAIService.sendMessage(request)
        );
    }
}