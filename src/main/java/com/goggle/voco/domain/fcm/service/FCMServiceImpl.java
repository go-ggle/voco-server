package com.goggle.voco.domain.fcm.service;

import com.goggle.voco.domain.user.domain.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FCMServiceImpl implements FCMService {

    public void sendToToken(User user, String title, String content) {
        String registrationToken = user.getFcmToken();

        Message message = Message.builder()
                .putData("title", title)
                .putData("content", content)
                .setToken(registrationToken)
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Successfully sent message: " + response);
    }
}
