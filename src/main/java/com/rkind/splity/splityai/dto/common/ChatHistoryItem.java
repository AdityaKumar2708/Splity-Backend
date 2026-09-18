package com.rkind.splity.splityai.dto.common;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistoryItem {

    private String messageId;

    private String role;

    private String message;

    private String messageType;

    private LocalDateTime timestamp;

    private List<MessageAttachment> attachments;

}