package com.goggle.voco.service;


import com.goggle.voco.dto.TrainRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {
    @Override
    public Mono<Void> startTrain(TrainRequestDto trainRequestDto) {
        WebClient client = WebClient.builder()
                .baseUrl("http://58.142.29.186:52424")
                .defaultCookie("cookieKey", "cookieValue")
                .build();

        return client.post()
                .uri("/train")
                .body(Mono.just(trainRequestDto), TrainRequestDto.class)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
