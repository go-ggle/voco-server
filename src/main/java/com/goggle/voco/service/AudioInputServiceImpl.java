package com.goggle.voco.service;

import com.goggle.voco.dto.AudioInputRequestDto;
import com.goggle.voco.dto.AudioInputResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

@Service
@Log4j2
@RequiredArgsConstructor
public class AudioInputServiceImpl implements AudioInputService{
    @Value("${AI_ADDRESS}")
    private String AI_ADDRESS;
    @Value("${FLASK_PORT}")
    private String FLASK_PORT;

    @Override
    public AudioInputResponseDto audioInput(MultipartFile audio, AudioInputRequestDto audioInputRequestDto) throws IOException {
        WebClient client = WebClient.builder()
                .baseUrl("http://" + AI_ADDRESS + ":" + FLASK_PORT)
                .defaultCookie("cookieKey", "cookieValue")
                .build();

        //byte[] bytes = audio.getBytes();
        //System.out.printf(bytes.toString());
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder
                .part("data", audioInputRequestDto)
                .header("Content-Type", "application/json");
        multipartBodyBuilder
                .part("audio", audio.getResource())
                .header("Content-Disposition",
                        "form-data; name=audio;")
                .header("Content-Type", "multipart/form-data");
                //.header("Content-Type", "audio/mpeg");

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
