package com.rkind.splity.splityai.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIUserContext {

    private Long userId;

    private String fullName;

    private String mobile;

    private String email;

    private String profileImage;

    private String preferredLanguage;

}