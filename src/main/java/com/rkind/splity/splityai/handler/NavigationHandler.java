package com.rkind.splity.splityai.handler;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;
import com.rkind.splity.splityai.enums.SplityIntent;
import org.springframework.stereotype.Component;

@Component
public class NavigationHandler implements AIHandler {

    @Override
    public DashboardChatResponse handle(DashboardChatRequest request) {

        DashboardChatResponse response = new DashboardChatResponse();

        response.setSuccess(true);
        response.setIntent(SplityIntent.APP_NAVIGATION);
        response.setNavigationRequired(true);

        String message = request.getMessage() == null
                ? ""
                : request.getMessage().toLowerCase().trim();

        if (contains(message, "wallet")) {

            setResponse(response, "wallet", "Opening Wallet...");

        } else if (contains(message, "split", "expense", "bill")) {

            setResponse(response, "split", "Opening Split Expenses...");

        } else if (contains(message, "travel", "train", "flight", "bus")) {

            setResponse(response, "travel", "Opening Travel...");

        } else if (contains(message, "group", "groups")) {

            setResponse(response, "group", "Opening Groups...");

        } else if (contains(message, "profile", "account")) {

            setResponse(response, "profile", "Opening Profile...");

        } else if (contains(message, "notification", "notifications")) {

            setResponse(response, "notification", "Opening Notifications...");

        } else if (contains(message, "setting", "settings")) {

            setResponse(response, "settings", "Opening Settings...");

        } else {

            setResponse(response, "dashboard", "Opening Dashboard...");
        }

        return response;
    }

    private void setResponse(
            DashboardChatResponse response,
            String target,
            String reply
    ) {
        response.setNavigationTarget(target);
        response.setReply(reply);
    }

    private boolean contains(
            String message,
            String... keywords
    ) {

        for (String keyword : keywords) {

            if (message.contains(keyword.toLowerCase())) {
                return true;
            }

        }

        return false;
    }

}