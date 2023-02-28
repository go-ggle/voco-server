package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.AudioInputRequestDto;
import com.goggle.voco.dto.AudioInputResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;

@Service
@Log4j2
@RequiredArgsConstructor
public class AudioInputServiceImpl implements AudioInputService{
    @Value("${AI_ADDRESS}")
    private String AI_ADDRESS;
    @Value("${FLASK_PORT}")
    private String FLASK_PORT;

    @Override
    public void audioInput(User user, Long textId, MultipartFile audio) throws IOException {
        URI uri = UriComponentsBuilder
                .fromUriString("http://" + AI_ADDRESS + ":" + FLASK_PORT)
                .path("/put_data")
                .queryParam("textId", textId)
                .queryParam("userId", user.getId())
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(textId + ".wav")
                .build();

        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(audio.getBytes(), fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .postForEntity(uri, requestEntity, String.class);

        /*WebClient client = WebClient.builder()
                .baseUrl("http://" + AI_ADDRESS + ":" + FLASK_PORT)
                .defaultCookie("cookieKey", "cookieValue")
                .build();

        //byte[] bytes = audio.getBytes();
        //System.out.printf(bytes.toString());
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder
                .part("audio", new ByteArrayResource(audio.getBytes()))
                .header("Content-Disposition",
                        "form-data; name=audio;")
                .header("Content-Type", "audio/wav");
                //.header("Content-Type", "audio/mpeg");

        AudioInputResponseDto audioInputResponseDto = client.post()
                .uri(uriBuilder -> uriBuilder.path("/put_data")
                        .queryParam("textId", textId)
                        .queryParam("userId", user.getId())
                        .build())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .retrieve()
                .bodyToMono(AudioInputResponseDto.class)
                .block();*/
    }
}
