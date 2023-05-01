package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TokenResponseDto;

public interface OAuthService {
    TokenResponseDto createKakaoUserToken(String token);
    User createKakaoUser(String kakaoId);
}
