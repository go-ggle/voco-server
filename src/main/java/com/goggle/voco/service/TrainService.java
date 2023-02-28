package com.goggle.voco.service;

import com.goggle.voco.dto.TrainRequestDto;

public interface TrainService {
    void startTrain(TrainRequestDto trainRequestDto) throws Exception;
}
