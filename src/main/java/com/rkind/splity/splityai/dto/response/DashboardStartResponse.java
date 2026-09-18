package com.rkind.splity.splityai.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStartResponse {

    private boolean success;

    private String conversationId;

    private String welcomeMessage;

    private String aiName;

    private String aiVersion;

    private boolean aiReady;

    private List<String> suggestions;

}