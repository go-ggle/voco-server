package com.goggle.voco.service;

import com.goggle.voco.dto.*;

public interface AuthService {

    UserResponseDto createUser(UserRequestDto userRequestDto);
    TokenResponseDto createToken(TokenRequestDto tokenRequestDto);
    TokenRenewResponseDto renewToken(TokenRenewRequestDto tokenRenewRequestDto);
}
