package com.goggle.voco.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRenewResponseDto {
    private String accessToken;
    private String refreshToken;
}
