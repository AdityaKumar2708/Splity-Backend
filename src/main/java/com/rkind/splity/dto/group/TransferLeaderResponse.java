package com.rkind.splity.dto.group;

public class TransferLeaderResponse {

    private boolean success;
    private String message;
    private Long newLeaderId;

    public TransferLeaderResponse() {
    }

    public TransferLeaderResponse(boolean success, String message, Long newLeaderId) {
        this.success = success;
        this.message = message;
        this.newLeaderId = newLeaderId;
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

    public Long getNewLeaderId() {
        return newLeaderId;
    }

    public void setNewLeaderId(Long newLeaderId) {
        this.newLeaderId = newLeaderId;
    }
}