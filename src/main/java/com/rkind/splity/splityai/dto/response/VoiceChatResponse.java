package com.rkind.splity.splityai.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceChatResponse {

    private boolean success;

    private String transcript;

    private String aiReply;

    private String audioUrl;

}