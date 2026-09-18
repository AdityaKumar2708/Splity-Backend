package com.rkind.splity.splityai.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import com.rkind.splity.splityai.model.AIMessage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIConversation {

    private String conversationId;

    private Long userId;

    private List<AIMessage> messages;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}