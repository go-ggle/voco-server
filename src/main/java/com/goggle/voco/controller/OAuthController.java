package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.KakaoTokenRequestDto;
import com.goggle.voco.dto.TokenResponseDto;
import com.goggle.voco.service.OAuthService;
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
@RequestMapping("/oauth")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "OAuth", description = "소셜 로그인 API")
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("kakao")
    @Operation(summary = "카카오 로그인", description = "OpenID 토큰으로 카카오 소셜 로그인을 진행한다.")
    public ResponseEntity<TokenResponseDto> createKakaoUserToken(@RequestBody KakaoTokenRequestDto kakaoTokenRequestDto) {

        TokenResponseDto tokenResponseDto = oAuthService.createKakaoUserToken(kakaoTokenRequestDto);

        return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
    }
}
