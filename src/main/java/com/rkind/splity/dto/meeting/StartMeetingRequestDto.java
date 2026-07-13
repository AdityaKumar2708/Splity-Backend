package com.rkind.splity.dto.meeting;

public class StartMeetingRequestDto {

    private Long groupId;
    private Long hostUserId;

    public StartMeetingRequestDto() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getHostUserId() {
        return hostUserId;
    }

    public void setHostUserId(Long hostUserId) {
        this.hostUserId = hostUserId;
    }
}