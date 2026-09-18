package com.rkind.splity.wallet.dto;

public class VerifyEmailOtpRequest {

    private Long userId;
    private String email;
    private String otp;

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}