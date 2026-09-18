package com.rkind.splity.splityai.handler;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;
import com.rkind.splity.splityai.enums.SplityIntent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SplitHandler implements AIHandler {

    @Override
    public DashboardChatResponse handle(DashboardChatRequest request) {

        DashboardChatResponse response = new DashboardChatResponse();

        response.setSuccess(true);
        response.setConversationId(request.getConversationId());
        response.setIntent(SplityIntent.SPLIT);

        response.setReply(
                "Opening Split Expenses. I can help you split bills, settle balances and manage group expenses."
        );

        response.setNavigationRequired(true);
        response.setNavigationTarget("split");

        response.setSuggestions(Arrays.asList(
                "Create Expense",
                "View Expenses",
                "Settle Balance",
                "Group Summary"
        ));

        return response;
    }
}