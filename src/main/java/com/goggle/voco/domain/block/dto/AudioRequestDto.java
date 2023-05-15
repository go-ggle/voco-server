package com.goggle.voco.domain.block.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AudioRequestDto {

    private Long id; //안씀
    private String text;
    private String audioPath; //안씀
    private Long voiceId;
    private Long interval;
    private Long language;
    private Long order; //안씀
}
