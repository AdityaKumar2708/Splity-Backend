package com.rkind.splity.dto;

public class CreateGroupRequest {
    private Long userId;
    private String name;
    private String code;
    private String dpUri;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDpUri() {
        return dpUri;
    }

    public void setDpUri(String dpUri) {
        this.dpUri = dpUri;
    }
}
