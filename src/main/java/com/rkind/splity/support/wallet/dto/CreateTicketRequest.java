package com.rkind.splity.support.wallet.dto;

import com.rkind.splity.support.wallet.enums.TicketCategory;

public class CreateTicketRequest {

    private Long userId;

    private TicketCategory category;

    private String subject;

    private String description;

    public CreateTicketRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
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
}