package com.rkind.splity.dto;

import java.util.List;

public class SocketMessageDto {
    private String type; // "INCOMING_CALL", "CALL_REJECTED", "USER_JOINED", "USER_LEFT", "MEETING_ENDED", "MUTE_CHANGED", "SPEAKER_CHANGED"
    private Long meetingId;
    private Long groupId;
    private Long hostUserId;
    private String meetingType;
    private Long senderId;
    private Long receiverId;
    private List<Long> participantIds;
    private Boolean muted;
    private Boolean speaker;


   public String getType() {
        return type;
   }

   public  void setType(String type) {
       this.type = type;
   }

   public  Long getMeetingId() {
       return  meetingId;
   }

   public   void setMeetingId(Long meetingId) {
       this.meetingId = meetingId;
   }

   public  Long getGroupId() {
       return groupId;
   }

   public   void setGroupId(Long groupId) {
       this.groupId = groupId;
   }

   public   Long getSenderId() {
       return  senderId;
   }

   public    void setSenderId(Long senderId) {
       this.senderId = senderId;
   }

   public Long getHostUserId() {
       return hostUserId;
   }

   public  void setHostUserId(Long hostUserId) {
       this.hostUserId = hostUserId;
   }

   public  String getMeetingType() {
       return  meetingType;
   }

   public   void setMeetingType(String meetingType) {
       this.meetingType = meetingType;
   }

   public Long getReceiverId() {
       return receiverId;
   }

   public   void setReceiverId(Long receiverId) {
       this.receiverId = receiverId;
   }

   public  List<Long> getParticipantIds() {
       return  participantIds;
   }

   public   void setParticipantIds(List<Long> participantIds) {
       this.participantIds = participantIds;
   }
   public  Boolean getMuted() {
       return  muted;
   }

   public    void setMuted(Boolean muted) {
       this.muted = muted;
   }

   public   Boolean getSpeaker() {
       return  speaker;
   }

   public     void setSpeaker(Boolean speaker) {
       this.speaker = speaker;
   }
}