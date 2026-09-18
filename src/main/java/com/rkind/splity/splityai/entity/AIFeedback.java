package com.rkind.splity.splityai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_feedback")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conversationId;

    private Long messageId;

    private Long userId;

    private Boolean helpful;

    @Lob
    private String feedback;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

}