package com.rkind.splity.service;

import com.rkind.splity.entity.UserSession;
import com.rkind.splity.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    public UserSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }


    public Optional<UserSession> findByDeviceId(String deviceId) {

        return userSessionRepository.findByDeviceId(deviceId);
    }

    public Optional<UserSession> findUserDeviceSession(
            Long userId,
            String deviceId
    ) {

        return userSessionRepository
                .findByUserIdAndDeviceId(userId, deviceId);
    }


    public Optional<UserSession> getActivePhoneSession(Long userId) {

        return userSessionRepository
                .findByUserIdAndDeviceTypeAndActiveTrue(
                        userId,
                        "PHONE"
                );
    }


    public List<UserSession> getActiveWebSessions(Long userId) {

        return userSessionRepository
                .findAllByUserIdAndDeviceTypeAndActiveTrue(
                        userId,
                        "WEB"
                );
    }

    public List<UserSession> getActiveSessions(Long userId) {

        return userSessionRepository
                .findAllByUserIdAndActiveTrue(userId);
    }


    public long countActiveWebSessions(Long userId) {

        return userSessionRepository
                .countByUserIdAndDeviceTypeAndActiveTrue(
                        userId,
                        "WEB"
                );
    }


    public boolean hasActivePhoneSession(Long userId) {

        return userSessionRepository
                .countByUserIdAndDeviceTypeAndActiveTrue(
                        userId,
                        "PHONE"
                ) > 0;
    }


    public boolean canCreateWebSession(Long userId) {

        long activeWebSessions =
                countActiveWebSessions(userId);

        return activeWebSessions < 2;
    }

    public UserSession createSession(
            Long userId,
            String deviceId,
            String deviceType,
            String platform,
            String fcmToken
    ) {


        Optional<UserSession> existingSession =
                userSessionRepository
                        .findByUserIdAndDeviceId(
                                userId,
                                deviceId
                        );

        if (existingSession.isPresent()) {

            UserSession session = existingSession.get();

            session.setDeviceType(deviceType);
            session.setPlatform(platform);
            session.setFcmToken(fcmToken);

            session.setActive(true);
            session.setLastActiveAt(LocalDateTime.now());

            return userSessionRepository.save(session);
        }

        if ("PHONE".equalsIgnoreCase(deviceType)) {

            long activePhoneSessions =
                    userSessionRepository
                            .countByUserIdAndDeviceTypeAndActiveTrue(
                                    userId,
                                    "PHONE"
                            );

            if (activePhoneSessions >= 1) {

                throw new RuntimeException(
                        "An active phone session already exists"
                );
            }
        }


        if ("WEB".equalsIgnoreCase(deviceType)) {

            long activeWebSessions =
                    userSessionRepository
                            .countByUserIdAndDeviceTypeAndActiveTrue(
                                    userId,
                                    "WEB"
                            );

            if (activeWebSessions >= 2) {

                throw new RuntimeException(
                        "Maximum 2 active web sessions allowed"
                );
            }
        }


        UserSession session =
                new UserSession();

        session.setUserId(userId);
        session.setDeviceId(deviceId);
        session.setDeviceType(deviceType);
        session.setPlatform(platform);
        session.setFcmToken(fcmToken);

        session.setActive(true);

        LocalDateTime now =
                LocalDateTime.now();

        session.setCreatedAt(now);
        session.setLastActiveAt(now);

        return userSessionRepository.save(session);
    }

    public UserSession updateFcmToken(
            Long userId,
            String deviceId,
            String fcmToken
    ) {

        UserSession session = userSessionRepository
                .findByUserIdAndDeviceId(userId, deviceId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found")
                );

        session.setFcmToken(fcmToken);
        session.setLastActiveAt(LocalDateTime.now());

        return userSessionRepository.save(session);
    }

    public UserSession updateLastActive(
            Long userId,
            String deviceId
    ) {

        UserSession session = userSessionRepository
                .findByUserIdAndDeviceId(userId, deviceId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found")
                );

        session.setLastActiveAt(LocalDateTime.now());

        return userSessionRepository.save(session);
    }

    public UserSession deactivateSession(
            Long userId,
            String deviceId
    ) {

        UserSession session = userSessionRepository
                .findByUserIdAndDeviceId(userId, deviceId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found")
                );

        session.setActive(false);
        session.setLastActiveAt(LocalDateTime.now());

        return userSessionRepository.save(session);
    }

    public UserSession activateSession(
            Long userId,
            String deviceId
    ) {

        UserSession session = userSessionRepository
                .findByUserIdAndDeviceId(userId, deviceId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found")
                );

        session.setActive(true);
        session.setLastActiveAt(LocalDateTime.now());

        return userSessionRepository.save(session);
    }

}