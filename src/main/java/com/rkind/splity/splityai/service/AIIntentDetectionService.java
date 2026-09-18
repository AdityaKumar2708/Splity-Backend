package com.rkind.splity.splityai.service;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.enums.SplityIntent;
import org.springframework.stereotype.Service;

@Service
public class AIIntentDetectionService {

    private final SplityIntentService splityIntentService;

    public AIIntentDetectionService(SplityIntentService splityIntentService) {
        this.splityIntentService = splityIntentService;
    }

    public SplityIntent detectIntent(DashboardChatRequest request) {

        if (request == null) {
            return SplityIntent.UNKNOWN;
        }

        return splityIntentService.detectIntent(request.getMessage());
    }
}