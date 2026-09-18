package com.rkind.splity.dto.group;

public class TransferLeaderRequest {

    private Long groupId;
    private Long currentLeaderId;
    private Long newLeaderId;

    public TransferLeaderRequest() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCurrentLeaderId() {
        return currentLeaderId;
    }

    public void setCurrentLeaderId(Long currentLeaderId) {
        this.currentLeaderId = currentLeaderId;
    }

    public Long getNewLeaderId() {
        return newLeaderId;
    }

    public void setNewLeaderId(Long newLeaderId) {
        this.newLeaderId = newLeaderId;
    }
}