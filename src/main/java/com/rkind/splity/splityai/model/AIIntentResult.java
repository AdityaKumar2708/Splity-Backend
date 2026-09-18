package com.rkind.splity.splityai.model;

import com.rkind.splity.splityai.enums.SplityIntent;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIIntentResult {

    private SplityIntent intent;

    private double confidence;

    private String reason;

}