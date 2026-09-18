package com.rkind.splity.support.wallet.dto;

public class StartChatRequest {

    private Long userId;

    public StartChatRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}