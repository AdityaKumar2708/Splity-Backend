package com.rkind.splity.support.wallet.dto;

public class AIResponse {

    private boolean success;

    private String reply;

    private boolean createTicket;

    private boolean transferToHuman;

    private String intent;

    public AIResponse() {
    }

    public AIResponse(boolean success,
                      String reply,
                      boolean createTicket,
                      boolean transferToHuman,
                      String intent) {

        this.success = success;
        this.reply = reply;
        this.createTicket = createTicket;
        this.transferToHuman = transferToHuman;
        this.intent = intent;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isCreateTicket() {
        return createTicket;
    }

    public void setCreateTicket(boolean createTicket) {
        this.createTicket = createTicket;
    }

    public boolean isTransferToHuman() {
        return transferToHuman;
    }

    public void setTransferToHuman(boolean transferToHuman) {
        this.transferToHuman = transferToHuman;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }
}