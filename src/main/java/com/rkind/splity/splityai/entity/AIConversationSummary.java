package com.rkind.splity.splityai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_conversation_summary")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIConversationSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String conversationId;

    @Lob
    private String summary;

    private Integer totalMessages;

    private Long userId;

    private LocalDateTime lastMessageAt;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

}