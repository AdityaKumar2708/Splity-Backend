package com.rkind.splity.dto;

public class HandleJoinRequestDto {

    private Long requestId;
    private boolean approve;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public boolean isApprove() {  
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }
}


