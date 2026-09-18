package com.rkind.splity.splityai.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIImageData {

    private String imageUrl;

    private String mimeType;

    private long size;

    private String base64;

}