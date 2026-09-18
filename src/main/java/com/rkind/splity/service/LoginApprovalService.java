package com.rkind.splity.service;

import com.rkind.splity.dto.LoginApprovalResponse;
import com.rkind.splity.entity.LoginApprovalRequest;
import com.rkind.splity.entity.User;
import com.rkind.splity.entity.UserSession;
import com.rkind.splity.repository.LoginApprovalRequestRepository;
import com.rkind.splity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginApprovalService {

    private final LoginApprovalRequestRepository approvalRepository;
    private final UserSessionService userSessionService;
    private final UserRepository userRepository;

    public LoginApprovalService(
            LoginApprovalRequestRepository approvalRepository,
            UserSessionService userSessionService,
            UserRepository userRepository
    ) {
        this.approvalRepository = approvalRepository;
        this.userSessionService = userSessionService;
        this.userRepository = userRepository;
    }

    public LoginApprovalRequest createApprovalRequest(
            Long userId,
            String deviceId,
            String deviceType,
            String platform
    ) {

        Optional<LoginApprovalRequest> existingRequest =
                approvalRepository
                        .findByUserIdAndDeviceIdAndStatus(
                                userId,
                                deviceId,
                                "PENDING"
                        );

        if (existingRequest.isPresent()) {
            return existingRequest.get();
        }

        LocalDateTime now = LocalDateTime.now();

        LoginApprovalRequest request =
                new LoginApprovalRequest();

        request.setUserId(userId);
        request.setDeviceId(deviceId);
        request.setDeviceType(deviceType);
        request.setPlatform(platform);

        request.setStatus("PENDING");

        request.setCreatedAt(now);

        request.setExpiresAt(
                now.plusMinutes(2)
        );

        return approvalRepository.save(request);
    }


    public Optional<LoginApprovalRequest> findById(
            Long requestId
    ) {

        return approvalRepository.findById(requestId);
    }



    public String getStatus(Long requestId) {

        LoginApprovalRequest request =
                approvalRepository.findById(requestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Approval request not found"
                                )
                        );

        if (request.getStatus().equals("PENDING")
                && LocalDateTime.now()
                .isAfter(request.getExpiresAt())) {

            request.setStatus("EXPIRED");

            approvalRepository.save(request);
        }

        return request.getStatus();
    }


    @Transactional
    public LoginApprovalRequest approveRequest(
            Long requestId
    ) {

        LoginApprovalRequest request =
                approvalRepository.findById(requestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Approval request not found"
                                )
                        );

        if (!request.getStatus().equals("PENDING")) {

            throw new RuntimeException(
                    "Approval request is no longer pending"
            );
        }

        if (LocalDateTime.now()
                .isAfter(request.getExpiresAt())) {

            request.setStatus("EXPIRED");

            approvalRepository.save(request);

            throw new RuntimeException(
                    "Approval request has expired"
            );
        }

        request.setStatus("APPROVED");

        approvalRepository.save(request);

        if ("PHONE".equalsIgnoreCase(
                request.getDeviceType()
        )) {

            Optional<UserSession> activePhone =
                    userSessionService
                            .getActivePhoneSession(
                                    request.getUserId()
                            );

            if (activePhone.isPresent()) {

                UserSession oldPhone =
                        activePhone.get();

                if (!oldPhone.getDeviceId()
                        .equals(request.getDeviceId())) {

                    userSessionService.deactivateSession(
                            request.getUserId(),
                            oldPhone.getDeviceId()
                    );
                }
            }
        }

        return request;
    }

    @Transactional
    public LoginApprovalRequest rejectRequest(
            Long requestId
    ) {

        LoginApprovalRequest request =
                approvalRepository.findById(requestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Approval request not found"
                                )
                        );
        if (!request.getStatus().equals("PENDING")) {
            throw new RuntimeException(
                    "Approval request is no longer pending"
            );
        }

        request.setStatus("REJECTED");

        return approvalRepository.save(request);
    }


    public boolean requiresApproval(
            Long userId,
            String deviceType,
            String deviceId
    ) {

        if ("PHONE".equalsIgnoreCase(deviceType)) {

            Optional<UserSession> activePhone =
                    userSessionService
                            .getActivePhoneSession(userId);

            return activePhone.isPresent()
                    && !activePhone.get()
                    .getDeviceId()
                    .equals(deviceId);
        }


        if ("WEB".equalsIgnoreCase(deviceType)) {

            return userSessionService
                    .getActivePhoneSession(userId)
                    .isPresent();
        }


        return false;
    }

    public LoginApprovalResponse checkApprovalByPhone(
            String phoneNumber,
            String deviceId,
            String deviceType,
            String platform
    ) {

        User user = userRepository
                .findByPhoneNumber(phoneNumber)
                .orElse(null);

        // Account hi nahi mila
        if (user == null) {

            return new LoginApprovalResponse(
                    null,
                    "NOT_REQUIRED",
                    false
            );
        }

        boolean required =
                requiresApproval(
                        user.getId(),
                        deviceType,
                        deviceId
                );

        if (!required) {

            return new LoginApprovalResponse(
                    null,
                    "NOT_REQUIRED",
                    false
            );
        }

        LoginApprovalRequest approvalRequest =
                createApprovalRequest(
                        user.getId(),
                        deviceId,
                        deviceType,
                        platform
                );

        return new LoginApprovalResponse(
                approvalRequest.getId(),
                approvalRequest.getStatus(),
                true
        );
    }


}