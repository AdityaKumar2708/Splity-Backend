package com.rkind.splity.splityai.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStartRequest {

    private Long userId;

    private String deviceId;

    private String appVersion;

    private String platform;

    private String language;

}