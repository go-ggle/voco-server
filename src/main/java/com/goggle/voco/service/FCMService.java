package com.goggle.voco.service;

import com.goggle.voco.domain.User;

public interface FCMService {
    void sendToToken(User user, String title, String content);
}
