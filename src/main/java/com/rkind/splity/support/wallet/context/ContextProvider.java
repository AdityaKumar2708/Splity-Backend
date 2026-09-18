package com.rkind.splity.support.wallet.context;

import com.rkind.splity.support.wallet.dto.AIRequest;
import com.rkind.splity.support.wallet.enums.AIIntent;

import java.util.Set;

public interface ContextProvider {

    Set<AIIntent> getSupportedIntents();

    String buildContext(AIRequest request);

}