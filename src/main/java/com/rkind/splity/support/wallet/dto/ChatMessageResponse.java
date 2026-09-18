package com.rkind.splity.support.wallet.dto;

import com.rkind.splity.support.wallet.enums.SenderType;

import java.time.LocalDateTime;

public class ChatMessageResponse {

    private boolean success;

    private String reply;

    private boolean createTicket;

    private String ticketNumber;

    private SenderType sender;

    private LocalDateTime createdAt;

    public ChatMessageResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isCreateTicket() {
        return createTicket;
    }

    public void setCreateTicket(boolean createTicket) {
        this.createTicket = createTicket;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public SenderType getSender() {
        return sender;
    }

    public void setSender(SenderType sender) {
        this.sender = sender;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}