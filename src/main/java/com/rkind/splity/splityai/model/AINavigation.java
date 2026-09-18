package com.rkind.splity.splityai.model;

import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AINavigation {

    private boolean required;

    // wallet, split, travel, profile...
    private String target;

    // Optional parameter
    private String parameter;

    // Screen title
    private String title;

    // Extra parameters (Future)
    private Map<String, Object> extras;

}