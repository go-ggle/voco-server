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
    private Long blockId;
    private Long projectId;
    private Long teamId;
    private Long interval;
}
