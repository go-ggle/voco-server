package com.goggle.voco.domain.fcm.service;

import com.goggle.voco.domain.user.domain.User;

public interface FCMService {
    void sendToToken(User user, String title, String content);
}
