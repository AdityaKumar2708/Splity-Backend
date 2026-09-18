package com.rkind.splity.splityai.handler;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;
import com.rkind.splity.splityai.enums.SplityIntent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TravelHandler implements AIHandler {

    @Override
    public DashboardChatResponse handle(DashboardChatRequest request) {

        DashboardChatResponse response = new DashboardChatResponse();

        response.setSuccess(true);
        response.setConversationId(request.getConversationId());
        response.setIntent(SplityIntent.TRAVEL);

        response.setReply(
                "Opening Travel. I can help you with train, flight and bus bookings."
        );

        response.setNavigationRequired(true);
        response.setNavigationTarget("travel");

        response.setSuggestions(Arrays.asList(
                "Book Train",
                "Book Flight",
                "Book Bus",
                "My Bookings"
        ));

        return response;
    }
}