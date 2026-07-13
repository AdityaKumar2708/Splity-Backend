package com.rkind.splity.dto.meeting;

public class MuteStatusRequestDto {
    private Long meetingId;
    private Long userId;
    private boolean muted;

    public Long getMeetingId() {
        return meetingId;
    }
    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public  Long getUserId() {
        return  userId;
    }

    public  void setUserId(Long userId) {
        this.userId = userId;
    }

    public  boolean isMuted() {
        return muted;
    }

    public  void setMuted(Boolean muted) {
        this.muted = muted;
    }
}