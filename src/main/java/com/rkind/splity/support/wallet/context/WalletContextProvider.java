package com.rkind.splity.support.wallet.context;

import com.rkind.splity.support.wallet.dto.AIRequest;
import com.rkind.splity.support.wallet.enums.AIIntent;
import com.rkind.splity.wallet.dto.WalletDetailsResponse;
import com.rkind.splity.wallet.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class WalletContextProvider implements ContextProvider {

    private final WalletService walletService;

    public WalletContextProvider(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public Set<AIIntent> getSupportedIntents() {
        return Set.of(AIIntent.WALLET_BALANCE);
    }

    @Override
    public String buildContext(AIRequest request) {

        WalletDetailsResponse wallet =
                walletService.getWallet(request.getUserId());

        if ("NOT_ACTIVATED".equals(wallet.getStatus())) {

            return """
                    Wallet Status: NOT_ACTIVATED
                    """;
        }

        return """
                Wallet Status: %s
                Wallet Balance: ₹%s
                """
                .formatted(
                        wallet.getStatus(),
                        wallet.getBalance()
                );
    }
}