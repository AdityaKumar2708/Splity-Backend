package com.rkind.splity.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {

        try {

            if (!FirebaseApp.getApps().isEmpty()) {
                return;
            }

            InputStream serviceAccount;

            File renderFile =
                    new File("/etc/secrets/firebase-service-account.json");

            if (renderFile.exists()) {

                System.out.println("Using Render Firebase Secret");

                serviceAccount =
                        new FileInputStream(renderFile);

            } else {

                System.out.println("Using Local Firebase JSON");

                serviceAccount =
                        getClass().getClassLoader().getResourceAsStream(
                                "firebase/firebase-service-account.json"
                        );

                if (serviceAccount == null) {
                    throw new RuntimeException(
                            "firebase-service-account.json not found in resources/firebase"
                    );
                }
            }

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

            System.err.println("========== FIREBASE ERROR ==========");
            e.printStackTrace();
            System.err.println("====================================");

            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }
}