package com.rkind.splity.dto.meeting;

import java.util.List;

public class MeetingResponseDto {

    private Long meetingId;
    private Long groupId;
    private Long hostUserId;
    private String roomId;
    private boolean active;
    private List<Long> participantIds;

    public MeetingResponseDto() {
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public  List<Long> getParticipantIds() {
        return  participantIds;
    }
    public  void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }
}