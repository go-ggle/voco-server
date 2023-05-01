package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TokenResponseDto;
import com.goggle.voco.service.OAuthService;
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
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("kakao")
    public ResponseEntity<TokenResponseDto> createKakaoUserToken(@RequestBody String token) {

        TokenResponseDto tokenResponseDto = oAuthService.createKakaoUserToken(token);

        return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
    }
}
