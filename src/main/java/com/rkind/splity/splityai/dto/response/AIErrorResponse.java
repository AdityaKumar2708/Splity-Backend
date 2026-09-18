package com.rkind.splity.splityai.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIErrorResponse {

    private boolean success;

    private String errorCode;

    private String errorMessage;

    private String timestamp;

}