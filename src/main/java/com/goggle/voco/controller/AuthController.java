package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TokenRequestDto;
import com.goggle.voco.dto.TokenResponseDto;
import com.goggle.voco.dto.UserRequestDto;
import com.goggle.voco.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@Log4j2
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("signup")
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto userRequestDto) {
        User user = authService.createUser(userRequestDto);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenResponseDto tokenResponseDto = authService.createToken(tokenRequestDto);

        return new ResponseEntity<>(tokenResponseDto, HttpStatus.CREATED);
    }
}
