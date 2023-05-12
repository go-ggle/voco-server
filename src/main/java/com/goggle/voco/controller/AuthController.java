package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.*;
import com.goggle.voco.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "로그인/회원가입 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("signup")
    @Operation(summary = "회원가입", description = "이메일과 비밀번호로 회원가입을 진행한다.")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = authService.createUser(userRequestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인을 진행한다.")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenResponseDto tokenResponseDto = authService.createToken(tokenRequestDto);

        return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
    }
    @PostMapping("renew")
    @Operation(summary = "토큰 갱신", description = "리프레시 토큰으로 액세스 토큰을 갱신한다.")
    public ResponseEntity<TokenRenewResponseDto> renew(@RequestBody TokenRenewRequestDto tokenRenewRequestDto) {
        TokenRenewResponseDto tokenRenewResponseDto = authService.renewToken(tokenRenewRequestDto);

        return new ResponseEntity<>(tokenRenewResponseDto, HttpStatus.OK);
    }
}
