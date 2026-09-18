package com.rkind.splity.support.wallet.entity;

import com.rkind.splity.support.wallet.enums.TicketCategory;
import com.rkind.splity.support.wallet.enums.TicketPriority;
import com.rkind.splity.support.wallet.enums.TicketStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "support_ticket",
        indexes = {
                @Index(name = "idx_ticket_number", columnList = "ticket_number"),
                @Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_status", columnList = "status")
        }
)
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_number", unique = true, nullable = false, length = 30)
    private String ticketNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority;

    @Column(columnDefinition = "TEXT")
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "last_message", columnDefinition = "TEXT")
    private String lastMessage;

    @Column(name = "is_bot_resolved")
    private Boolean botResolved = false;

    @Column(name = "assigned_support_id")
    private Long assignedSupportId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "closed_by_support_id")
    private Long closedBySupportId;

    @Column(name="escalated_at")
    private LocalDateTime escalatedAt;

    public SupportTicket() {
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = TicketStatus.OPEN;
        }

        if (priority == null) {
            priority = TicketPriority.MEDIUM;
        }

        if (botResolved == null) {
            botResolved = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
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

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Boolean getBotResolved() {
        return botResolved;
    }

    public void setBotResolved(Boolean botResolved) {
        this.botResolved = botResolved;
    }

    public Long getAssignedSupportId() {
        return assignedSupportId;
    }

    public void setAssignedSupportId(Long assignedSupportId) {
        this.assignedSupportId = assignedSupportId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Long getClosedBySupportId() {
        return closedBySupportId;
    }

    public void setClosedBySupportId(Long closedBySupportId) {
        this.closedBySupportId = closedBySupportId;
    }

    public LocalDateTime getEscalatedAt() {
        return escalatedAt;
    }

    public void setEscalatedAt(LocalDateTime escalatedAt) {
        this.escalatedAt = escalatedAt;
    }
}