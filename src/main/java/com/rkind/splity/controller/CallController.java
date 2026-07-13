package com.rkind.splity.controller;

import com.rkind.splity.dto.call.CallRequestDto;
import com.rkind.splity.service.CallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/call")
public class CallController {

    private final CallService callService;

    public CallController(CallService callService) {
        this.callService = callService;
    }

    @PostMapping("/request")
    public ResponseEntity<Void> requestCall(
            @RequestBody CallRequestDto request) {

        callService.requestCall(request);

        return ResponseEntity.ok().build();
    }
}