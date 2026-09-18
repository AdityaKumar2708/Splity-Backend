package com.rkind.splity.support.wallet.dto;

public class ChatStartResponse {

    private boolean success;

    private String message;

    public ChatStartResponse() {
    }

    public ChatStartResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

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
}