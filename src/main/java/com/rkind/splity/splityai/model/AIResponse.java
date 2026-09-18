package com.rkind.splity.splityai.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIResponse {

    private String reply;

    private Integer promptTokens;

    private Integer completionTokens;

    private Integer totalTokens;

    private Long responseTime;

    private String model;

}