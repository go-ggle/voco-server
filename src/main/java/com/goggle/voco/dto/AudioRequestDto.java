package com.goggle.voco.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AudioRequestDto {

    private String text;
    private int language;
    private Long userId;
}
