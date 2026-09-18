package com.rkind.splity.wallet.dto;

public class SendEmailOtpRequest {

    private Long userId;
    private String email;

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}