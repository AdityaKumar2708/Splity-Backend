package com.rkind.splity.service;

import com.google.firebase.auth.FirebaseToken;
import com.rkind.splity.entity.User;
import com.rkind.splity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserAuthService {

    private final UserRepository userRepository;

    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOrCreateUser(FirebaseToken token) {

    String firebaseUid = token.getUid();
    String phone = (String) token.getClaims().get("phone_number");

    return userRepository.findByFirebaseUid(firebaseUid)
            .orElseGet(() -> {

                User user = new User();
                user.setFirebaseUid(firebaseUid);
                user.setPhoneNumber(phone);
                user.setCreatedAt(LocalDateTime.now());

                return userRepository.save(user);
            });
}

}
