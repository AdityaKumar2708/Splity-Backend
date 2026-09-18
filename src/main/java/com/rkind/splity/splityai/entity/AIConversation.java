package com.rkind.splity.splityai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_conversations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIConversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String conversationId;

    @Column(nullable = false)
    private Long userId;

    private String title;

    @Builder.Default
    private String status = "ACTIVE";

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    private Boolean fromCache;

    private Double confidenceScore;

    private String reason;

}