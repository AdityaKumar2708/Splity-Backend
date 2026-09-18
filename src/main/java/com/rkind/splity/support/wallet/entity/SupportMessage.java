package com.rkind.splity.support.wallet.entity;

import com.rkind.splity.support.wallet.enums.SenderType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "support_message",
        indexes = {
                @Index(name = "idx_ticket_id", columnList = "ticket_id"),
                @Index(name = "idx_created_at", columnList = "created_at")
        }
)
public class SupportMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private SupportTicket ticket;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SenderType sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "is_read")
    private Boolean read = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public SupportMessage() {
    }

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if (read == null) {
            read = false;
        }

    }

    public Long getId() {
        return id;
    }

    public SupportTicket getTicket() {
        return ticket;
    }

    public void setTicket(SupportTicket ticket) {
        this.ticket = ticket;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SenderType getSender() {
        return sender;
    }

    public void setSender(SenderType sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}