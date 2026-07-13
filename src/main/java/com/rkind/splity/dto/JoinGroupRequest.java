package com.rkind.splity.dto;

public class JoinGroupRequest {

    private Long userId;
    private String code;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {   
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
