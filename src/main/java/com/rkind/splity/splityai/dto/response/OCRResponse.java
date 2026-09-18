package com.rkind.splity.splityai.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OCRResponse {

    private boolean success;

    private String extractedText;

    private String detectedLanguage;

}