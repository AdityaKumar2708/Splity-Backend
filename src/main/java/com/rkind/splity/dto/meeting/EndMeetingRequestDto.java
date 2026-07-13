package com.rkind.splity.dto.meeting;

public class EndMeetingRequestDto {

    private Long meetingId;
    private Long hostUserId;

    public EndMeetingRequestDto() {
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getHostUserId() {
        return hostUserId;
    }

    public void setHostUserId(Long hostUserId) {
        this.hostUserId = hostUserId;
    }
}