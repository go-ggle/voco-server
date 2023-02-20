package com.goggle.voco.controller;

import com.goggle.voco.dto.TrainResponseDto;
import com.goggle.voco.repository.TrainRepository;
import com.goggle.voco.service.TrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/trains")
@Log4j2
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @GetMapping("/{id}")
    public ResponseEntity<TrainResponseDto> getTrain(@PathVariable Long id) {
        TrainResponseDto trainResponseDto = trainService.findTrainById(id);

        return new ResponseEntity<>(trainResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("")
    public ResponseEntity<TrainResponseDto> createTrain(@RequestBody TrainRequestDto trainRequestDto) {
        TrainResponseDto trainResponseDto = trainService.createTrain(trainRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(trainResponseDto);
    }

}
