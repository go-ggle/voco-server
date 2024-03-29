package com.goggle.voco.domain.user.dto;

import com.goggle.voco.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoiceResponseDto {

    private Long id;
    private String nickname;

    public static VoiceResponseDto from(User user) {
        return new VoiceResponseDto(
                user.getId(),
                user.getNickname()
        );
    }
}
