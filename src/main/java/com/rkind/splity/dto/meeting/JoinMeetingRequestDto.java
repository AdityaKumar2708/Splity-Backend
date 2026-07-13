package com.rkind.splity.dto.meeting;

public class JoinMeetingRequestDto {

    private Long meetingId;
    private Long userId;

    public JoinMeetingRequestDto() {
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