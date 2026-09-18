package com.rkind.splity.dto;
public class LoginApprovalResponse {

    private Long requestId;
    private String status;
    private boolean approvalRequired;

    public LoginApprovalResponse() {
    }

    public LoginApprovalResponse(
            Long requestId,
            String status,
            boolean approvalRequired
    ) {
        this.requestId = requestId;
        this.status = status;
        this.approvalRequired = approvalRequired;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isApprovalRequired() {
        return approvalRequired;
    }

    public void setApprovalRequired(boolean approvalRequired) {
        this.approvalRequired = approvalRequired;
    }
}