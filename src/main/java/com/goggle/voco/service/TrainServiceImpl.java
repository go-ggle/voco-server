package com.goggle.voco.service;

import com.goggle.voco.dto.TrainRequestDto;
import com.goggle.voco.dto.TrainResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Log4j2
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService{
    @Override
    public void startTrain(TrainRequestDto trainRequestDto) {
        WebClient client = WebClient.builder()
                .baseUrl("http://58.142.29.186:52424")
                .defaultCookie("cookieKey", "cookieValue")
                .build();

        client.post()
                .uri("/train")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(trainRequestDto)
                .retrieve()
                .bodyToMono(TrainResponseDto.class)
                .subscribe();
    }
}
