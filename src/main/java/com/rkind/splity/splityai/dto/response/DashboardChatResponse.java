package com.rkind.splity.splityai.dto.response;

import com.rkind.splity.splityai.enums.SplityIntent;
import com.rkind.splity.splityai.model.AINavigation;
import com.rkind.splity.splityai.model.AIResponseMetadata;
import com.rkind.splity.splityai.model.AITokenUsage;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardChatResponse {

    private boolean success;

    private String conversationId;

    private String messageId;

    private String reply;

    private SplityIntent intent;

    private boolean navigationRequired;

    private String navigationTarget;

    private List<String> suggestions;

    private AINavigation navigation;

    private AIResponseMetadata metadata;

    private AITokenUsage tokenUsage;

    private ReceiptAnalysisResponse receipt;

}