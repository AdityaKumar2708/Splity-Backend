package com.rkind.splity.service;

import com.rkind.splity.dto.notification.NotificationSettingsRequestDto;
import com.rkind.splity.dto.notification.NotificationSettingsResponseDto;
import com.rkind.splity.entity.NotificationSettings;
import com.rkind.splity.repository.NotificationSettingsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class NotificationSettingsService {

    private final NotificationSettingsRepository repository;

    public NotificationSettingsService(
            NotificationSettingsRepository repository
    ) {
        this.repository = repository;
    }

    /*
     * Returns settings.
     * Creates default settings if not found.
     */
    public NotificationSettingsResponseDto getSettings(
            Long userId
    ) {

        NotificationSettings settings =
                repository.findByUserId(userId)
                        .orElseGet(() -> createDefaultSettings(userId));

        return toResponse(settings);
    }

    /*
     * Save / Update
     */
    public NotificationSettingsResponseDto saveSettings(
            NotificationSettingsRequestDto request
    ) {

        NotificationSettings settings =
                repository.findByUserId(request.getUserId())
                        .orElse(new NotificationSettings());

        settings.setUserId(request.getUserId());

        // General
        settings.setMuteNotifications(request.isMuteNotifications());
        settings.setNotificationSound(request.getNotificationSound());
        settings.setMeetingTone(request.getMeetingTone());
        settings.setVibrationMode(request.getVibrationMode());

        // Messages
        // Messages
        settings.setMessageNotification(
                request.isMessageNotification());

        settings.setMessagePreview(
                request.isMessagePreview());

        settings.setGroupNotification(
                request.isGroupNotification());

        settings.setMeetingNotification(
                request.isMeetingNotification());

        settings.setMentionNotification(
                request.isMentionNotification());

        // Do Not Disturb
        settings.setDndEnabled(request.isDndEnabled());

        if (request.isDndEnabled()) {
            settings.setDndFrom(request.getDndFrom());
            settings.setDndTo(request.getDndTo());
        } else {
            settings.setDndFrom(null);
            settings.setDndTo(null);
        }

        // Video Meeting
        settings.setVideoMeetingNotification(
                request.isVideoMeetingNotification());

        settings.setVideoMeetingTone(
                request.getVideoMeetingTone());

        settings = repository.save(settings);

        return toResponse(settings);
    }
    /*
     * Default settings
     */
    private NotificationSettings createDefaultSettings(
            Long userId
    ) {

        NotificationSettings settings = new NotificationSettings();

        settings.setUserId(userId);

        // General
        settings.setMuteNotifications(false);
        settings.setNotificationSound("Default");
        settings.setMeetingTone("Default");
        settings.setVibrationMode("Normal");

        // Messages
        // Messages
        settings.setMessageNotification(true);

        settings.setMessagePreview(true);

        settings.setGroupNotification(true);

        settings.setMeetingNotification(true);

        settings.setMentionNotification(true);

        // Do Not Disturb
        settings.setDndEnabled(false);
        settings.setDndFrom(LocalTime.of(22, 0));
        settings.setDndTo(LocalTime.of(7, 0));

        // Video Meeting
        settings.setVideoMeetingNotification(true);
        settings.setVideoMeetingTone("Default");

        return repository.save(settings);
    }

    /*
     * Entity -> DTO
     */
    private NotificationSettingsResponseDto toResponse(
            NotificationSettings settings
    ) {

        NotificationSettingsResponseDto dto =
                new NotificationSettingsResponseDto();

        dto.setUserId(settings.getUserId());

        dto.setMuteNotifications(
                settings.isMuteNotifications());

        dto.setNotificationSound(
                settings.getNotificationSound());

        dto.setMeetingTone(
                settings.getMeetingTone());

        dto.setVibrationMode(
                settings.getVibrationMode());

        dto.setMessagePreview(
                settings.isMessagePreview());

        dto.setGroupNotification(
                settings.isGroupNotification());

        dto.setMeetingNotification(
                settings.isMeetingNotification());

        dto.setMentionNotification(
                settings.isMentionNotification());

        dto.setMessageNotification(
                settings.isMessageNotification());

        dto.setDndEnabled(
                settings.isDndEnabled());

        dto.setDndFrom(
                settings.getDndFrom());

        dto.setDndTo(
                settings.getDndTo());

        dto.setVideoMeetingNotification(
                settings.isVideoMeetingNotification());

        dto.setVideoMeetingTone(
                settings.getVideoMeetingTone());

        System.out.println("=================================");
        System.out.println("ENTITY = " + settings.isMessageNotification());
        System.out.println("DTO = " + dto.isMessageNotification());
        System.out.println("=================================");

        return dto;
    }

    public java.util.List<com.rkind.splity.dto.notification.NotificationSoundDto> getNotificationSounds() {

        java.util.List<com.rkind.splity.dto.notification.NotificationSoundDto> list =
                new java.util.ArrayList<>();

        list.add(new com.rkind.splity.dto.notification.NotificationSoundDto(
                "Default",
                "notification_default.mp3"
        ));

        list.add(new com.rkind.splity.dto.notification.NotificationSoundDto(
                "Dragon",
                "dragon_sound.mp3"
        ));

        list.add(new com.rkind.splity.dto.notification.NotificationSoundDto(
                "Notification 62",
                "notification62.mp3"
        ));

        list.add(new com.rkind.splity.dto.notification.NotificationSoundDto(
                "Universe",
                "univers_notification.mp3"
        ));

        return list;
    }

    public NotificationSettingsResponseDto resetSettings(Long userId) {

        NotificationSettings settings =
                repository.findByUserId(userId)
                        .orElseGet(() -> createDefaultSettings(userId));

        settings.setMuteNotifications(false);
        settings.setNotificationSound("Default");
        settings.setMeetingTone("Default");
        settings.setVibrationMode("Normal");

        settings.setMessageNotification(true);

        settings.setMessagePreview(true);
        settings.setGroupNotification(true);
        settings.setMeetingNotification(true);
        settings.setMentionNotification(true);

        settings.setMessagePreview(true);
        settings.setGroupNotification(true);
        settings.setMeetingNotification(true);
        settings.setMentionNotification(true);

        settings.setDndEnabled(false);
        settings.setDndFrom(LocalTime.of(22, 0));
        settings.setDndTo(LocalTime.of(7, 0));

        settings.setVideoMeetingNotification(true);
        settings.setVideoMeetingTone("Default");

        System.out.println("=================================");
        System.out.println("ENTITY BEFORE SAVE = " + settings.isMessageNotification());
        System.out.println("=================================");

        repository.save(settings);

        System.out.println("SAVED ID = " + settings.getId());
        System.out.println("SAVED USER = " + settings.getUserId());
        System.out.println("SAVED MESSAGE = " + settings.isMessageNotification());

        return toResponse(settings);
    }
}