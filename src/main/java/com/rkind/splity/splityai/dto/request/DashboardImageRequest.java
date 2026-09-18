package com.rkind.splity.splityai.dto.request;

import com.rkind.splity.splityai.model.AIImageData;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardImageRequest {

    private Long userId;

    private String conversationId;

    private AIImageData image;

    private String prompt;

}