package com.rkind.splity.splityai.handler;

import com.rkind.splity.splityai.dto.request.DashboardChatRequest;
import com.rkind.splity.splityai.dto.response.DashboardChatResponse;

public interface AIHandler {

    DashboardChatResponse handle(DashboardChatRequest request);

}