package com.goggle.voco.controller;

import com.goggle.voco.dto.TrainRequestDto;
import com.goggle.voco.service.TrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/train")
@Log4j2
@RequiredArgsConstructor
public class TrainController {
    private final TrainService trainService;

    @PostMapping()
    public ResponseEntity<String> startTraining(@RequestBody TrainRequestDto trainRequestDto){
        trainService.startTrain(trainRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body("모델 학습이 시작되었습니다.");
    }
}
