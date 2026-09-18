package com.rkind.splity.bills.recharge.dto;

import java.math.BigDecimal;

public class RechargePlanResponse {

    private Long planId;
    private String planName;
    private BigDecimal amount;
    private String validity;
    private String smsBenefit;
    private String voiceBenefit;
    private String dataBenefit;
    private String ottBenefit;
    private String description;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getSmsBenefit() {
        return smsBenefit;
    }

    public void setSmsBenefit(String smsBenefit) {
        this.smsBenefit = smsBenefit;
    }

    public String getVoiceBenefit() {
        return voiceBenefit;
    }

    public void setVoiceBenefit(String voiceBenefit) {
        this.voiceBenefit = voiceBenefit;
    }

    public String getDataBenefit() {
        return dataBenefit;
    }

    public void setDataBenefit(String dataBenefit) {
        this.dataBenefit = dataBenefit;
    }

    public String getOttBenefit() {
        return ottBenefit;
    }

    public void setOttBenefit(String ottBenefit) {
        this.ottBenefit = ottBenefit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
