package com.rkind.splity.splityai.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AITokenUsage {

    private int promptTokens;

    private int completionTokens;

    private int totalTokens;


}