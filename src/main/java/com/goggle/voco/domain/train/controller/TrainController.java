package com.goggle.voco.domain.train.controller;

import com.goggle.voco.domain.train.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trains")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Train", description = "트레이닝 API")
public class TrainController {
    private final TrainService trainService;

    @PostMapping("")
    @Operation(summary = "사용자 모델 트레이닝", description = "Flask에서 사용자 모델을 트레이닝한다.")
    public ResponseEntity<String> startTrain(@AuthenticationPrincipal Long userId) throws Exception {
        trainService.startTrain(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
