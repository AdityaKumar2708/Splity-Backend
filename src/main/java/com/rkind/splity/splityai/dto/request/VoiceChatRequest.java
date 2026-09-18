package com.rkind.splity.splityai.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceChatRequest {

    private Long userId;

    private String conversationId;

    private String audioUrl;

    private String language;

}