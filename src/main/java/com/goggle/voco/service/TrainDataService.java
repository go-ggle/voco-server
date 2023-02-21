package com.goggle.voco.service;

import com.goggle.voco.dto.TrainDataRequestDto;
import com.goggle.voco.dto.TrainDataResponseDto;

public interface TrainDataService {
    TrainDataResponseDto findTrainById(Long id);
    TrainDataResponseDto createTrain(TrainDataRequestDto trainDataRequestDto);
}
