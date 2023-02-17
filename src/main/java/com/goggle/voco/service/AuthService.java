package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TokenRequestDto;
import com.goggle.voco.dto.UserRequestDto;

public interface AuthService {

    User createUser(UserRequestDto userRequestDto);
    String createToken(TokenRequestDto tokenRequestDto);
}
