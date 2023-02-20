package com.goggle.voco.service;

import com.goggle.voco.domain.Train;
import com.goggle.voco.dto.TrainRequestDto;
import com.goggle.voco.dto.TrainResponseDto;
import com.goggle.voco.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService{
    private final TrainRepository trainRepository;

    @Override
    public TrainResponseDto findTrainById(Long id) {
        Train train = trainRepository.getReferenceById(id);

        TrainResponseDto trainResponseDto = new TrainResponseDto();
        trainResponseDto.setId(train.getId());
        trainResponseDto.setText_id(train.getText_id());
        trainResponseDto.setText(train.getText());

        return trainResponseDto;
    }

    @Override
    public TrainResponseDto createTrain(TrainRequestDto trainRequestDto) {
        Train train = new Train();
        train.setText_id(trainRequestDto.getText_id());
        train.setText(trainRequestDto.getText());

        trainRepository.save(train);

        TrainResponseDto trainResponseDto = new TrainResponseDto();
        trainResponseDto.setId(train.getId());
        trainResponseDto.setText_id(train.getText_id());
        trainResponseDto.setText(train.getText());

        return trainResponseDto;
    }
}
