package com.rkind.splity.splityai.handler;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;
import com.rkind.splity.splityai.enums.SplityIntent;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class WalletHandler implements AIHandler {

    @Override
    public DashboardChatResponse handle(DashboardChatRequest request) {

        DashboardChatResponse response = new DashboardChatResponse();

        response.setSuccess(true);
        response.setConversationId(request.getConversationId());
        response.setIntent(SplityIntent.WALLET);

        response.setReply(
                "Opening Wallet. I can help you with wallet balance, transactions, adding money and payments."
        );

        response.setNavigationRequired(true);
        response.setNavigationTarget("wallet");

        response.setSuggestions(Arrays.asList(
                "Check Balance",
                "Transaction History",
                "Add Money",
                "Send Money"
        ));

        return response;
    }
}