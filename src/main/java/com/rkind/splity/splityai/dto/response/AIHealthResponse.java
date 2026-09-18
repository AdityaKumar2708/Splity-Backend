package com.rkind.splity.splityai.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIHealthResponse {

    private boolean healthy;

    private String provider;

    private String model;

    private long responseTime;

    private String version;

}