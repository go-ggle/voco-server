package com.goggle.voco.service;

import com.goggle.voco.dto.TrainDataRequestDto;
import com.goggle.voco.dto.TrainDataResponseDto;
import com.goggle.voco.dto.TrainDatumResponseDto;

public interface TrainDataService {
    TrainDatumResponseDto findTrainById(Long id);
    TrainDatumResponseDto createTrain(TrainDataRequestDto trainDataRequestDto);
    TrainDataResponseDto findAllTrainData();
}
