package com.rkind.splity.splityai.dto.common;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIUsageInfo {

    private int totalRequests;

    private int totalTokens;

    private int promptTokens;

    private int completionTokens;

    private long averageResponseTime;

}