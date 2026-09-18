package com.rkind.splity.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.rkind.splity.dto.ApprovalActionRequest;
import com.rkind.splity.dto.CheckLoginApprovalRequest;
import com.rkind.splity.dto.CreateLoginApprovalRequest;
import com.rkind.splity.dto.LoginApprovalResponse;
import com.rkind.splity.entity.LoginApprovalRequest;
import com.rkind.splity.entity.User;
import com.rkind.splity.entity.UserSession;
import com.rkind.splity.repository.UserRepository;
import com.rkind.splity.service.FirebaseNotificationService;
import com.rkind.splity.service.LoginApprovalService;

import com.rkind.splity.service.UserSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/login-approval")
public class LoginApprovalController {

    private final LoginApprovalService loginApprovalService;
    private final UserSessionService userSessionService;
    private final FirebaseNotificationService firebaseNotificationService;
    private final UserRepository userRepository;

    public LoginApprovalController(
            LoginApprovalService loginApprovalService,
            UserSessionService userSessionService,
            FirebaseNotificationService firebaseNotificationService,
            UserRepository userRepository
    ) {
        this.loginApprovalService = loginApprovalService;
        this.userSessionService = userSessionService;
        this.firebaseNotificationService = firebaseNotificationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkApproval(
            @RequestBody CreateLoginApprovalRequest request
    ) {

        boolean required =
                loginApprovalService.requiresApproval(
                        request.getUserId(),
                        request.getDeviceType(),
                        request.getDeviceId()
                );

        if (!required) {

            return ResponseEntity.ok(
                    new LoginApprovalResponse(
                            null,
                            "NOT_REQUIRED",
                            false
                    )
            );
        }

        LoginApprovalRequest approvalRequest =
                loginApprovalService.createApprovalRequest(
                        request.getUserId(),
                        request.getDeviceId(),
                        request.getDeviceType(),
                        request.getPlatform()
                );

        return ResponseEntity.ok(
                new LoginApprovalResponse(
                        approvalRequest.getId(),
                        approvalRequest.getStatus(),
                        true
                )
        );
    }

    @GetMapping("/status/{requestId}")
    public ResponseEntity<?> getStatus(
            @PathVariable Long requestId
    ) {

        String status =
                loginApprovalService.getStatus(requestId);

        return ResponseEntity.ok(
                new LoginApprovalResponse(
                        requestId,
                        status,
                        true
                )
        );
    }

    @PostMapping("/approve/{requestId}")
    public ResponseEntity<?> approve(
            @PathVariable Long requestId,
            @RequestBody ApprovalActionRequest actionRequest
    ) {

        try {

            if (actionRequest == null
                    || actionRequest.getIdToken() == null
                    || actionRequest.getIdToken().trim().isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body("Firebase ID token is required");
            }

            FirebaseToken decodedToken =
                    FirebaseAuth.getInstance()
                            .verifyIdToken(
                                    actionRequest.getIdToken()
                            );


            String firebaseUid =
                    decodedToken.getUid();

            User user =
                    userRepository
                            .findByFirebaseUid(firebaseUid)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "User not found"
                                    )
                            );

            LoginApprovalRequest approvalRequest =
                    loginApprovalService
                            .findById(requestId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Approval request not found"
                                    )
                            );

            if (!approvalRequest
                    .getUserId()
                    .equals(user.getId())) {

                return ResponseEntity
                        .status(403)
                        .body("You are not authorized to approve this request");
            }

            LoginApprovalRequest approvedRequest =
                    loginApprovalService
                            .approveRequest(requestId);


            return ResponseEntity.ok(
                    new LoginApprovalResponse(
                            approvedRequest.getId(),
                            approvedRequest.getStatus(),
                            true
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    @PostMapping("/reject/{requestId}")
    public ResponseEntity<?> reject(
            @PathVariable Long requestId,
            @RequestBody ApprovalActionRequest actionRequest
    ) {

        try {

            if (actionRequest == null
                    || actionRequest.getIdToken() == null
                    || actionRequest.getIdToken().trim().isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body("Firebase ID token is required");
            }

            FirebaseToken decodedToken =
                    FirebaseAuth.getInstance()
                            .verifyIdToken(
                                    actionRequest.getIdToken()
                            );


            String firebaseUid =
                    decodedToken.getUid();


            User user =
                    userRepository
                            .findByFirebaseUid(firebaseUid)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "User not found"
                                    )
                            );

            LoginApprovalRequest approvalRequest =
                    loginApprovalService
                            .findById(requestId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Approval request not found"
                                    )
                            );

            if (!approvalRequest
                    .getUserId()
                    .equals(user.getId())) {

                return ResponseEntity
                        .status(403)
                        .body("You are not authorized to reject this request");
            }

            LoginApprovalRequest rejectedRequest =
                    loginApprovalService
                            .rejectRequest(requestId);


            return ResponseEntity.ok(
                    new LoginApprovalResponse(
                            rejectedRequest.getId(),
                            rejectedRequest.getStatus(),
                            true
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/check-phone")
    public ResponseEntity<?> checkApprovalByPhone(
            @RequestBody CheckLoginApprovalRequest request
    ) {

        try {

            LoginApprovalResponse response =
                    loginApprovalService.checkApprovalByPhone(
                            request.getPhoneNumber(),
                            request.getDeviceId(),
                            request.getDeviceType(),
                            request.getPlatform()
                    );

            if (!response.isApprovalRequired()) {
                return ResponseEntity.ok(response);
            }

            LoginApprovalRequest approvalRequest =
                    loginApprovalService
                            .findById(response.getRequestId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Approval request not found"
                                    )
                            );


            Optional<UserSession> phoneSession =
                    userSessionService.getActivePhoneSession(
                            approvalRequest.getUserId()
                    );


            // Existing phone session nahi mili
            if (phoneSession.isEmpty()) {

                return ResponseEntity.ok(
                        new LoginApprovalResponse(
                                null,
                                "NOT_REQUIRED",
                                false
                        )
                );
            }


            UserSession existingPhone =
                    phoneSession.get();


            String fcmToken =
                    existingPhone.getFcmToken();


            if (fcmToken == null
                    || fcmToken.trim().isEmpty()) {

                return ResponseEntity.badRequest().body(
                        "Existing phone does not have FCM token"
                );
            }


            firebaseNotificationService
                    .sendLoginApprovalNotification(
                            fcmToken,
                            approvalRequest.getId()
                    );


            return ResponseEntity.ok(response);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}