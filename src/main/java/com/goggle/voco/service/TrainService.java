package com.goggle.voco.service;

import com.goggle.voco.dto.TrainRequestDto;
import com.goggle.voco.dto.TrainResponseDto;

public interface TrainService {
    TrainResponseDto findTrainById(Long id);
    TrainResponseDto createTrain(TrainRequestDto trainRequestDto);
}
