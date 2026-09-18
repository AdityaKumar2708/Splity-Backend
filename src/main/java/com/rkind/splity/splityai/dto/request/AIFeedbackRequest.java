package com.rkind.splity.splityai.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIFeedbackRequest {

    private Long userId;

    private String conversationId;

    private String messageId;

    private boolean helpful;

    private String feedback;

}