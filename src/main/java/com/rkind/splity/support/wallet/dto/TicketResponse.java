package com.rkind.splity.support.wallet.dto;

import com.rkind.splity.support.wallet.enums.TicketCategory;
import com.rkind.splity.support.wallet.enums.TicketPriority;
import com.rkind.splity.support.wallet.enums.TicketStatus;

import java.time.LocalDateTime;

public class TicketResponse {

    private String ticketNumber;

    private TicketStatus status;

    private String message;

    private String subject;
    private String description;
    private TicketCategory category;
    private TicketPriority priority;
    private LocalDateTime createdAt;
    private String lastMessage;

    public TicketResponse() {
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}