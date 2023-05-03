package com.goggle.voco.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AudioRequestDto {

    private String text;
    private Long language;
    private Long voiceId;
    private Long interval;
    private Long order;
}
