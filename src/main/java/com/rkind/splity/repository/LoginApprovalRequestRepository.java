package com.rkind.splity.repository;

import com.rkind.splity.entity.LoginApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginApprovalRequestRepository
        extends JpaRepository<LoginApprovalRequest, Long> {

    // Kisi specific approval request ko ID se find karna
    Optional<LoginApprovalRequest> findById(Long id);

    // User ki pending approval request
    Optional<LoginApprovalRequest> findByUserIdAndStatus(
            Long userId,
            String status
    );

    // User ki saari pending requests
    List<LoginApprovalRequest> findAllByUserIdAndStatus(
            Long userId,
            String status
    );

    // Specific new device ki pending request
    Optional<LoginApprovalRequest> findByUserIdAndDeviceIdAndStatus(
            Long userId,
            String deviceId,
            String status
    );
}