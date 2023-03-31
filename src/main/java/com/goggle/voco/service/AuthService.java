package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TokenRequestDto;
import com.goggle.voco.dto.TokenResponseDto;
import com.goggle.voco.dto.UserRequestDto;
import com.goggle.voco.dto.UserResponseDto;

public interface AuthService {

    UserResponseDto createUser(UserRequestDto userRequestDto);
    TokenResponseDto createToken(TokenRequestDto tokenRequestDto);
}
