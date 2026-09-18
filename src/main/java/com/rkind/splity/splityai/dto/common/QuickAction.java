package com.rkind.splity.splityai.dto.common;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuickAction {

    private String id;

    private String title;

    private String description;

    private String action;

    private String icon;

}