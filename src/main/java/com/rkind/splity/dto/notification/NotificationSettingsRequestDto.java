package com.rkind.splity.dto.notification;

import java.time.LocalTime;

public class NotificationSettingsRequestDto {

    private Long userId;

    private boolean muteNotifications;

    private String notificationSound;

    private String meetingTone;

    private String vibrationMode;

    private boolean messagePreview;

    private boolean groupNotification;

    private boolean meetingNotification;

    private boolean mentionNotification;

    private boolean dndEnabled;

    private LocalTime dndFrom;


    private LocalTime dndTo;

    private boolean videoMeetingNotification;

    private String videoMeetingTone;
    private boolean messageNotification;

    public NotificationSettingsRequestDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isMuteNotifications() {
        return muteNotifications;
    }

    public void setMuteNotifications(boolean muteNotifications) {
        this.muteNotifications = muteNotifications;
    }

    public String getNotificationSound() {
        return notificationSound;
    }

    public void setNotificationSound(String notificationSound) {
        this.notificationSound = notificationSound;
    }

    public String getMeetingTone() {
        return meetingTone;
    }

    public void setMeetingTone(String meetingTone) {
        this.meetingTone = meetingTone;
    }

    public String getVibrationMode() {
        return vibrationMode;
    }

    public void setVibrationMode(String vibrationMode) {
        this.vibrationMode = vibrationMode;
    }

    public boolean isMessagePreview() {
        return messagePreview;
    }

    public void setMessagePreview(boolean messagePreview) {
        this.messagePreview = messagePreview;
    }

    public boolean isGroupNotification() {
        return groupNotification;
    }

    public void setGroupNotification(boolean groupNotification) {
        this.groupNotification = groupNotification;
    }

    public boolean isMeetingNotification() {
        return meetingNotification;
    }

    public void setMeetingNotification(boolean meetingNotification) {
        this.meetingNotification = meetingNotification;
    }

    public boolean isMentionNotification() {
        return mentionNotification;
    }

    public void setMentionNotification(boolean mentionNotification) {
        this.mentionNotification = mentionNotification;
    }

    public boolean isDndEnabled() {
        return dndEnabled;
    }

    public void setDndEnabled(boolean dndEnabled) {
        this.dndEnabled = dndEnabled;
    }

    public LocalTime getDndFrom() {
        return dndFrom;
    }

    public void setDndFrom(LocalTime dndFrom) {
        this.dndFrom = dndFrom;
    }

    public LocalTime getDndTo() {
        return dndTo;
    }

    public void setDndTo(LocalTime dndTo) {
        this.dndTo = dndTo;
    }

    public boolean isVideoMeetingNotification() {
        return videoMeetingNotification;
    }

    public void setVideoMeetingNotification(boolean videoMeetingNotification) {
        this.videoMeetingNotification = videoMeetingNotification;
    }

    public String getVideoMeetingTone() {
        return videoMeetingTone;
    }

    public void setVideoMeetingTone(String videoMeetingTone) {
        this.videoMeetingTone = videoMeetingTone;
    }

    public  boolean isMessageNotification() {
        return messageNotification;
    }
    public void setMessageNotification(boolean messageNotification) {
        this.messageNotification = messageNotification;
    }
}