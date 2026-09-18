package com.rkind.splity.wallet.dto;

import java.math.BigDecimal;

public class VerifyPaymentResponse {

    private boolean success;
    private String message;
    private BigDecimal walletBalance;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }
}