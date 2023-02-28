package com.goggle.voco.controller;

import com.goggle.voco.dto.TrainDataRequestDto;
import com.goggle.voco.dto.TrainDataResponseDto;
import com.goggle.voco.service.TrainDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/train-data")
@Log4j2
@RequiredArgsConstructor
public class TrainDataController {

    private final TrainDataService trainDataService;

    @GetMapping("/{id}")
    public ResponseEntity<TrainDataResponseDto> getTrain(@PathVariable Long id) {
        TrainDataResponseDto trainDataResponseDto = trainDataService.findTrainById(id);

        return new ResponseEntity<>(trainDataResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("")
    public ResponseEntity<TrainDataResponseDto> createTrain(@RequestBody TrainDataRequestDto trainDataRequestDto) {
        TrainDataResponseDto trainDataResponseDto = trainDataService.createTrain(trainDataRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(trainDataResponseDto);
    }

}
