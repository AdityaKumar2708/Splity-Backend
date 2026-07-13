package com.rkind.splity.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FirebaseNotificationService {

    public void sendNotification(
            String token,
            Long groupId,
            Long senderId,
            String title,
            String body
    ) {

        if (token == null || token.trim().isEmpty()) {
            System.out.println("FCM Token is empty.");
            return;
        }

        try {

            Message message = Message.builder()
                    .setToken(token)

                    .putData("type", "CHAT")
                    .putData("groupId", String.valueOf(groupId))
                    .putData("senderId", String.valueOf(senderId))
                    .putData("title", title)
                    .putData("body", body)

                    .build();

            String response = FirebaseMessaging.getInstance().send(message);

            System.out.println("================================");
            System.out.println("FCM Notification Sent");
            System.out.println("Token : " + token);
            System.out.println("Group : " + groupId);
            System.out.println("Sender: " + senderId);
            System.out.println("Title : " + title);
            System.out.println("Body  : " + body);
            System.out.println("Response : " + response);
            System.out.println("================================");

        } catch (Exception e) {

            System.out.println("================================");
            System.out.println("FCM SEND FAILED");
            e.printStackTrace();
            System.out.println("================================");

        }
    }
}