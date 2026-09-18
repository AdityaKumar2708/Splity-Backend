package com.rkind.splity.splityai.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIMessage {

    private String messageId;

    private String role;

    private String content;

    private String type;

    private LocalDateTime timestamp;

}