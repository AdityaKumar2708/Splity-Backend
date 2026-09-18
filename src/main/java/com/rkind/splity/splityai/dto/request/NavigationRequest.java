package com.rkind.splity.splityai.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NavigationRequest {

    private Long userId;

    private String currentScreen;

    private String destination;

}