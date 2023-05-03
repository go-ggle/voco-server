package com.goggle.voco.service;

import com.goggle.voco.domain.Train;
import com.goggle.voco.dto.*;
import com.goggle.voco.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
