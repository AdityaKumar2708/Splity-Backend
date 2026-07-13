package com.rkind.splity.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "notification_settings")
public class NotificationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private boolean muteNotifications = false;

    @Column(nullable = false)
    private String notificationSound = "Default";

    @Column(nullable = false)
    private String meetingTone = "Default";

    @Column(nullable = false)
    private String vibrationMode = "Normal";

    @Column(nullable = false)
    private boolean messagePreview = true;

    @Column(nullable = false)
    private boolean groupNotification = true;

    @Column(nullable = false)
    private boolean meetingNotification = true;

    @Column(nullable = false)
    private boolean mentionNotification = true;

    @Column(nullable = false)
    private boolean dndEnabled = false;

    @Column(nullable = true)
    private LocalTime dndFrom;

    @Column(nullable = true)
    private LocalTime dndTo;

    @Column(nullable = false)
    private boolean videoMeetingNotification = true;

    @Column(nullable = false)
    private String videoMeetingTone = "Default";

    @Column(nullable = false)
    private boolean messageNotification;

    public NotificationSettings() {
    }

    public Long getId() {
        return id;
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
        return  messageNotification;
    }


    public  void setMessageNotification(boolean messageNotification) {
        this.messageNotification = messageNotification;
    }
}