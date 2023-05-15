package com.goggle.voco.domain.train.service;

import com.goggle.voco.domain.train.dto.TrainDataRequestDto;
import com.goggle.voco.domain.train.dto.TrainDataResponseDto;
import com.goggle.voco.domain.train.dto.TrainDatumResponseDto;

public interface TrainDataService {
    TrainDatumResponseDto findTrainById(Long id);
    TrainDatumResponseDto createTrain(TrainDataRequestDto trainDataRequestDto);
    TrainDataResponseDto findAllTrainData();
}
