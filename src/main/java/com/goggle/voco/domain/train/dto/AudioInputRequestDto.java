package com.goggle.voco.domain.train.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AudioInputRequestDto {
    private Long textId;
    private Long userId;
}
