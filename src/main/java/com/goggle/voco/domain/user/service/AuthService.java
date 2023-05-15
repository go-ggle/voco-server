package com.goggle.voco.domain.user.service;

import com.goggle.voco.domain.user.dto.*;

public interface AuthService {

    UserResponseDto createUser(UserRequestDto userRequestDto);
    TokenResponseDto createToken(TokenRequestDto tokenRequestDto);
    TokenRenewResponseDto renewToken(TokenRenewRequestDto tokenRenewRequestDto);
}
