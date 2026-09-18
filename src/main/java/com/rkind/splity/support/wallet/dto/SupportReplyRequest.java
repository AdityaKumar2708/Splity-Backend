package com.rkind.splity.support.wallet.dto;

public class SupportReplyRequest {

    private String ticketNumber;
    private Long supportId;
    private String message;

    public SupportReplyRequest() {
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Long getSupportId() {
        return supportId;
    }

    public void setSupportId(Long supportId) {
        this.supportId = supportId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}