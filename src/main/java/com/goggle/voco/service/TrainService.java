package com.goggle.voco.service;

import com.goggle.voco.dto.TrainRequestDto;

public interface TrainService {
    void finishTrain(Long userId);
    void startTrain(TrainRequestDto trainRequestDto) throws Exception;
}
