package com.goggle.voco.domain.train.service;

public interface TrainService {
    void finishTrain(Long userId);
    void startTrain(Long userId) throws Exception;
}
