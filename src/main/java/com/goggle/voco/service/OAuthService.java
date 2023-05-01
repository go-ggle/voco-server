package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.KakaoTokenRequestDto;
import com.goggle.voco.dto.KakaoUserResponseDto;
import com.goggle.voco.dto.TokenResponseDto;

public interface OAuthService {
    TokenResponseDto createKakaoUserToken(KakaoTokenRequestDto kakaoTokenRequestDto);
    User createKakaoUser(KakaoUserResponseDto kakaoUserResponseDto);
}
