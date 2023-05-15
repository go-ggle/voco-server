package com.goggle.voco.domain.user.service;

import com.goggle.voco.domain.user.domain.User;
import com.goggle.voco.domain.user.dto.KakaoTokenRequestDto;
import com.goggle.voco.domain.user.dto.KakaoUserResponseDto;
import com.goggle.voco.domain.user.dto.TokenResponseDto;

public interface OAuthService {
    TokenResponseDto createKakaoUserToken(KakaoTokenRequestDto kakaoTokenRequestDto);
    User createKakaoUser(KakaoUserResponseDto kakaoUserResponseDto);
}
