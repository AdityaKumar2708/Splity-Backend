package com.rkind.splity.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {

        try {

            if (!FirebaseApp.getApps().isEmpty()) {
                return;
            }

            InputStream serviceAccount =
                    new ClassPathResource(
                            "firebase/firebase-service-account.json"
                    ).getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(serviceAccount)
                    )
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println("====================================");
            System.out.println("Firebase initialized successfully.");
            System.out.println("====================================");

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to initialize Firebase",
                    e
            );

        }

    }

}