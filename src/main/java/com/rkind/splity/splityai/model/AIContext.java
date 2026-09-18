package com.rkind.splity.splityai.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIContext {

    private Long userId;

    private String userName;

    private String language;

    private String currentScreen;

    private String currentModule;

    private String currentGroupId;

    private String currentConversationId;

    private String timezone;

    private boolean loggedIn;

}