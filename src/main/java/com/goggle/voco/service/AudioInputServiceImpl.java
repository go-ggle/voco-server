package com.goggle.voco.service;

import com.goggle.voco.dto.AudioInputRequestDto;
import com.goggle.voco.dto.AudioInputResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
@Log4j2
@RequiredArgsConstructor
public class AudioInputServiceImpl implements AudioInputService{
    @Override
    public AudioInputResponseDto audioInput(MultipartFile audio, AudioInputRequestDto audioInputRequestDto) throws IOException{
        WebClient client = WebClient.builder()
                .baseUrl("http://58.142.29.186:52424")
                .defaultCookie("cookieKey", "cookieValue")
                .build();

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder
                .part("data", audioInputRequestDto)
                .header("Content-Type", "application/json");
        multipartBodyBuilder
                .part("audio", new ByteArrayResource(audio.getBytes()))
                .header("Content-Type", "audio/mpeg");

        AudioInputResponseDto audioInputResponseDto = client.post()
                .uri("/put_data")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .retrieve()
                .bodyToMono(AudioInputResponseDto.class)
                .block();

        return audioInputResponseDto;
    }
}
