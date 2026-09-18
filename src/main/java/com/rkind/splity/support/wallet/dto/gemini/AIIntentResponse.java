package com.rkind.splity.support.wallet.dto.gemini;

public class AIIntentResponse {

    private String intent;
    private int confidence;

    public AIIntentResponse() {
    }

    public AIIntentResponse(String intent, int confidence) {
        this.intent = intent;
        this.confidence = confidence;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }
}