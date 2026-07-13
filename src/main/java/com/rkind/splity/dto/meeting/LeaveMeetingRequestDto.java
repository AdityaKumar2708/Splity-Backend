package com.rkind.splity.dto.meeting;

public class LeaveMeetingRequestDto {

    private Long meetingId;
    private Long userId;

    public LeaveMeetingRequestDto() {
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}