package com.rkind.splity.dto.meeting;

public class SpeakerStatusRequestDto {
    private Long meetingId;
    private Long userId;
    private boolean speaker;

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

    // FIX: Changed return type to primitive boolean and getter name to isSpeaker
    public boolean isSpeaker() {
        return speaker;
    }

    // FIX: Changed parameter type to primitive boolean
    public void setSpeaker(boolean speaker) {
        this.speaker = speaker;
    }
}