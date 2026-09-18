package com.rkind.splity.splityai.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NavigationResponse {

    private boolean success;

    private boolean navigationRequired;

    private String target;

    private String title;

    private String message;

}