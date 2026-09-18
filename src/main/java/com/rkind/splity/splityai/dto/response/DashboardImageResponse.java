package com.rkind.splity.splityai.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardImageResponse {

    private boolean success;

    private String extractedText;

    private String aiReply;

    private String detectedLanguage;

}