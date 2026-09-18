package com.rkind.splity.splityai.dto.request;

import com.rkind.splity.splityai.model.AIImageData;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardChatRequest {

    private Long userId;

    private String conversationId;

    private String message;

    private String language;

    private String currentScreen;

    private String currentModule;

    private AIImageData image;

}