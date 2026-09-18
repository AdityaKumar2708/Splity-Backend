package com.rkind.splity.support.wallet.context;

import com.rkind.splity.support.wallet.dto.AIRequest;
import com.rkind.splity.support.wallet.enums.AIIntent;
import com.rkind.splity.support.wallet.service.PaymentContextService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PaymentContextProvider implements ContextProvider {

    private final PaymentContextService paymentContextService;

    public PaymentContextProvider(PaymentContextService paymentContextService) {
        this.paymentContextService = paymentContextService;
    }

    @Override
    public Set<AIIntent> getSupportedIntents() {

        return Set.of(
                AIIntent.PAYMENT_PENDING,
                AIIntent.PAYMENT_FAILED,
                AIIntent.REFUND_STATUS,
                AIIntent.TRANSACTION_HISTORY
        );
    }

    @Override
    public String buildContext(AIRequest request) {
        return paymentContextService.buildContext(
                request.getUserId(),
                request.getMessage()
        );
    }
}