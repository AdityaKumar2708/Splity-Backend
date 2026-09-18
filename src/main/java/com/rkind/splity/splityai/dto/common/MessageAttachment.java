package com.rkind.splity.splityai.dto.common;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageAttachment {

    private String id;

    private String fileName;

    private String fileType;

    private String fileUrl;

    private long fileSize;

    private String thumbnailUrl;

}