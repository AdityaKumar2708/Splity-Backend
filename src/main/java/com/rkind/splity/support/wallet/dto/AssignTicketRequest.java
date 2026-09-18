package com.rkind.splity.support.wallet.dto;

public class AssignTicketRequest {

    private String ticketNumber;
    private Long supportId;

    public AssignTicketRequest() {
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
}