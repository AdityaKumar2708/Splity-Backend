package com.rkind.splity.splityai.dto.request;

import com.rkind.splity.splityai.model.AIImageData;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OCRRequest {

    private Long userId;

    private AIImageData image;

    private String language;

}