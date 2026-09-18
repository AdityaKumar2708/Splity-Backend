package com.rkind.splity.support.wallet.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentContextService {

    public String buildContext(Long userId, String message) {

        return """
                Payment information is currently unavailable.
                Ask the user for the Transaction ID or Razorpay Order ID.
                """;
    }
}