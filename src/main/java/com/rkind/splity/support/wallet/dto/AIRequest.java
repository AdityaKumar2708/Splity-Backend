package com.rkind.splity.support.wallet.dto;

public class AIRequest {

    private Long userId;

    private String ticketNumber;

    private String message;

    public AIRequest() {
    }

    public AIRequest(Long userId, String ticketNumber, String message) {
        this.userId = userId;
        this.ticketNumber = ticketNumber;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}