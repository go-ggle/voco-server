package com.goggle.voco.service;

import com.goggle.voco.dto.TrainRequestDto;
import reactor.core.publisher.Mono;

public interface TrainService {
    Mono<Void> startTrain(TrainRequestDto trainRequestDto);
}
