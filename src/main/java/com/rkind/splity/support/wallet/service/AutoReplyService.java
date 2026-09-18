package com.rkind.splity.support.wallet.service;

import com.rkind.splity.support.wallet.dto.ChatMessageResponse;
import org.springframework.stereotype.Service;

@Service
public class AutoReplyService {

    public ChatMessageResponse generateReply(String message) {

        ChatMessageResponse response = new ChatMessageResponse();

        if (message == null || message.trim().isEmpty()) {

            response.setSuccess(true);
            response.setReply("Please describe your issue.");
            response.setCreateTicket(false);

            return response;
        }

        String text = message.toLowerCase().trim();

        // Payment Pending
        if (text.contains("pending")) {

            response.setSuccess(true);
            response.setReply(
                    "Please enter your Transaction ID so I can check the current payment status."
            );

            return response;
        }

        // Payment Failed
        if (text.contains("failed")) {

            response.setSuccess(true);
            response.setReply(
                    "Please enter your Transaction ID. I'll verify whether the payment failed or was reversed."
            );

            return response;
        }

        // Refund
        if (text.contains("refund")) {

            response.setSuccess(true);
            response.setReply(
                    "Refunds are usually processed within 24-48 hours after a failed transaction."
            );

            return response;
        }

        // Wallet
        if (text.contains("wallet")) {

            response.setSuccess(true);
            response.setReply(
                    "Please tell me the exact wallet issue. For example: balance not updated, add money failed, or wallet blocked."
            );

            return response;
        }

        // Default
        response.setSuccess(true);
        response.setReply(
                "I couldn't understand your issue. If this is related to Wallet or Payments, please explain it in more detail."
        );

        return response;
    }

}