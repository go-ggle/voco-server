package com.goggle.voco.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AudioRequestDto {

    private String text;
    private Long language;
    private Long userId;
    private Long blockId;
    private Long projectId;
    private Long interval;
}
