package com.rkind.splity.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.rkind.splity.dto.FirebaseLoginRequest;
import com.rkind.splity.entity.User;
import com.rkind.splity.service.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAuthService userAuthService;

    public AuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/firebase-login")
    public ResponseEntity<?> firebaseLogin(
            @RequestBody FirebaseLoginRequest request) {

        try {
            FirebaseToken decodedToken =
                    FirebaseAuth.getInstance().verifyIdToken(request.getIdToken());

            User user = userAuthService.getOrCreateUser(decodedToken);

            return ResponseEntity.ok(user);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid Firebase Token");
        }
    }

    
}