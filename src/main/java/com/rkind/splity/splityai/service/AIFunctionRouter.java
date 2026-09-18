package com.rkind.splity.splityai.service;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;
import com.rkind.splity.splityai.enums.SplityIntent;
import com.rkind.splity.splityai.handler.*;
import org.springframework.stereotype.Service;

@Service
public class AIFunctionRouter {

    private final GeminiHandler geminiHandler;
    private final NavigationHandler navigationHandler;
    private final WalletHandler walletHandler;
    private final SplitHandler splitHandler;
    private final TravelHandler travelHandler;

    public AIFunctionRouter(
            GeminiHandler geminiHandler,
            NavigationHandler navigationHandler,
            WalletHandler walletHandler,
            SplitHandler splitHandler,
            TravelHandler travelHandler
    ) {

        this.geminiHandler = geminiHandler;
        this.navigationHandler = navigationHandler;
        this.walletHandler = walletHandler;
        this.splitHandler = splitHandler;
        this.travelHandler = travelHandler;
    }

    public DashboardChatResponse route(
            SplityIntent intent,
            DashboardChatRequest request
    ) {

        switch (intent) {

            case WALLET:
                return walletHandler.handle(request);

            case SPLIT:
                return splitHandler.handle(request);

            case TRAVEL:
                return travelHandler.handle(request);

            case APP_NAVIGATION:
                return navigationHandler.handle(request);

            default:
                return geminiHandler.handle(request);
        }
    }


    private DashboardChatResponse createNavigationResponse(
            SplityIntent intent,
            String reply,
            String target
    ) {

        DashboardChatResponse response =
                new DashboardChatResponse();

        response.setSuccess(true);
        response.setIntent(intent);
        response.setReply(reply);
        response.setNavigationRequired(true);
        response.setNavigationTarget(target);

        return response;
    }
}