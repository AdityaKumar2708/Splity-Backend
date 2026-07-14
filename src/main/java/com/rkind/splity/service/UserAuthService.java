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

        System.out.println("SERVICE 1");

        String firebaseUid = token.getUid();
        String phone = (String) token.getClaims().get("phone_number");

        System.out.println("SERVICE 2");

        User user = userRepository.findByFirebaseUid(firebaseUid)
                .orElseGet(() -> {

                    System.out.println("SERVICE 3");

                    User u = new User();
                    u.setFirebaseUid(firebaseUid);
                    u.setPhoneNumber(phone);
                    u.setCreatedAt(LocalDateTime.now());

                    System.out.println("SERVICE 4");

                    return userRepository.save(u);
                });

        System.out.println("SERVICE 5");

        return user;
    }

}
