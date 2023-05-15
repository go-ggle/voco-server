package com.goggle.voco.domain.train.service;

import com.goggle.voco.domain.train.domain.Train;
import com.goggle.voco.domain.train.dto.TrainDataRequestDto;
import com.goggle.voco.domain.train.dto.TrainDataResponseDto;
import com.goggle.voco.domain.train.dto.TrainDatumResponseDto;
import com.goggle.voco.domain.train.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class TrainDataServiceImpl implements TrainDataService {
    private final TrainRepository trainRepository;

    @Override
    public TrainDatumResponseDto findTrainById(Long id) {
        Train train = trainRepository.getReferenceById(id);
        return TrainDatumResponseDto.from(train);
    }

    @Override
    public TrainDatumResponseDto createTrain(TrainDataRequestDto trainDataRequestDto) {
        Train train = new Train();
        train.setTextId(trainDataRequestDto.getTextId());
        train.setText(trainDataRequestDto.getText());

        trainRepository.save(train);
        return TrainDatumResponseDto.from(train);
    }

    @Override
    public TrainDataResponseDto findAllTrainData() {
        List<Train> trains = trainRepository.findAll();
        List<TrainDatumResponseDto> trainDatumResponseDtos = trains.stream()
                .map(TrainDatumResponseDto::from).toList();

        return new TrainDataResponseDto(trainDatumResponseDtos);
    }
}
