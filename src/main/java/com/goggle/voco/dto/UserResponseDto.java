package com.goggle.voco.dto;

import com.goggle.voco.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String fcmToken;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getEmail(),
            user.getNickname(),
            user.getFcmToken()
        );
    }
}
