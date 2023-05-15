package com.goggle.voco.domain.train.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainDataResponseDto {
    private List<TrainDatumResponseDto> trainData;
}
