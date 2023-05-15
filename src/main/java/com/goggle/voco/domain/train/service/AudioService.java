package com.goggle.voco.domain.train.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface AudioService {
    public HttpStatus audioInput(Long userId, Long textId, MultipartFile audio) throws IOException;
    public Mono<ByteArrayResource> getAudio(Long userId, Long textId);
}

