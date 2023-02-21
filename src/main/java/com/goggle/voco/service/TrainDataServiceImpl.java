package com.goggle.voco.service;

import com.goggle.voco.domain.Train;
import com.goggle.voco.dto.TrainDataRequestDto;
import com.goggle.voco.dto.TrainDataResponseDto;
import com.goggle.voco.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class TrainDataServiceImpl implements TrainDataService {
    private final TrainRepository trainRepository;

    @Override
    public TrainDataResponseDto findTrainById(Long id) {
        Train train = trainRepository.getReferenceById(id);

        TrainDataResponseDto trainDataResponseDto = new TrainDataResponseDto();
        trainDataResponseDto.setId(train.getId());
        trainDataResponseDto.setText_id(train.getText_id());
        trainDataResponseDto.setText(train.getText());

        return trainDataResponseDto;
    }

    @Override
    public TrainDataResponseDto createTrain(TrainDataRequestDto trainDataRequestDto) {
        Train train = new Train();
        train.setText_id(trainDataRequestDto.getTextId());
        train.setText(trainDataRequestDto.getText());

        trainRepository.save(train);

        TrainDataResponseDto trainDataResponseDto = new TrainDataResponseDto();
        trainDataResponseDto.setId(train.getId());
        trainDataResponseDto.setText_id(train.getText_id());
        trainDataResponseDto.setText(train.getText());

        return trainDataResponseDto;
    }
}
