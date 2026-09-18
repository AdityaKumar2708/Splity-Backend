package com.rkind.splity.splityai.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIResponseMetadata {

    private String provider;

    private String model;

    private long responseTime;

    private String requestId;

}