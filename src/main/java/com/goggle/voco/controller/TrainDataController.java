package com.goggle.voco.controller;

import com.goggle.voco.dto.TrainDataRequestDto;
import com.goggle.voco.dto.TrainDataResponseDto;
import com.goggle.voco.service.TrainDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "TrainMetadata", description = "트레이닝 메타데이터 API")
public class TrainDataController {

    private final TrainDataService trainDataService;

    @GetMapping("/{id}")
    @Operation(summary = "트레이닝 문장 조회", description = "트레이닝할 문장 데이터를 조회한다.")
    public ResponseEntity<TrainDataResponseDto> getTrain(@PathVariable Long id) {
        TrainDataResponseDto trainDataResponseDto = trainDataService.findTrainById(id);

        return new ResponseEntity<>(trainDataResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("")
    @Operation(summary = "트레이닝 문장 입력", description = "트레이닝할 문장 데이터를 입력한다.")
    public ResponseEntity<TrainDataResponseDto> createTrain(@RequestBody TrainDataRequestDto trainDataRequestDto) {
        TrainDataResponseDto trainDataResponseDto = trainDataService.createTrain(trainDataRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(trainDataResponseDto);
    }

}
