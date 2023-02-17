package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.UserRequestDto;

public interface UserService {

    User createUser(UserRequestDto userRequestDto);
}
