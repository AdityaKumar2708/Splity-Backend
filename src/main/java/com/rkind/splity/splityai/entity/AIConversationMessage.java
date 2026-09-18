package com.rkind.splity.splityai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_conversation_messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIConversationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String conversationId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    private String messageType;

    private String detectedIntent;

    private Integer promptTokens;

    private Integer completionTokens;

    private Integer totalTokens;

    private Long responseTime;
    private String modelName;
    private Boolean fromCache;

    private Double confidenceScore;

    private Boolean successful;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

}