package com.goggle.voco.domain.train.service;

import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;

@Service
@Log4j2
@RequiredArgsConstructor
public class AudioServiceImpl implements AudioService {
    @Value("${ai.address}")
    private String AI_ADDRESS;
    @Value("${ai.port}")
    private String FLASK_PORT;

    @Override
    public HttpStatus audioInput(Long userId, Long textId, MultipartFile audio) throws IOException {
        URI uri = UriComponentsBuilder
                .fromUriString("http://" + AI_ADDRESS + ":" + FLASK_PORT)
                .path("/put_data")
                .queryParam("textId", textId)
                .queryParam("userId", userId)
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

        return response.getStatusCode();

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


    @Override
    public Mono<ByteArrayResource> getAudio(Long userId, Long textId){
        WebClient webClient = WebClient.create("http://" + AI_ADDRESS + ":" + FLASK_PORT);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/audios")
                        .queryParam("user_id", "{userId}")
                        .queryParam("text_id", "{textId}")
                        .build(userId.intValue(), textId.intValue()))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK))
                        return response.bodyToMono(ByteArrayResource.class);
                    if (response.statusCode().equals(HttpStatus.NOT_FOUND))
                        throw new NotFoundException(ErrorCode.AUDIO_NOT_FOUND);
                    return null;
                });
    }
}
