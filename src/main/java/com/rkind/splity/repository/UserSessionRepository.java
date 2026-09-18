package com.rkind.splity.repository;

import com.rkind.splity.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByDeviceId(String deviceId);

    Optional<UserSession> findByUserIdAndDeviceId(
            Long userId,
            String deviceId
    );

    Optional<UserSession> findByUserIdAndDeviceTypeAndActiveTrue(
            Long userId,
            String deviceType
    );

    List<UserSession> findAllByUserIdAndDeviceTypeAndActiveTrue(
            Long userId,
            String deviceType
    );

    List<UserSession> findAllByUserIdAndActiveTrue(
            Long userId
    );

    long countByUserIdAndDeviceTypeAndActiveTrue(
            Long userId,
            String deviceType
    );
}