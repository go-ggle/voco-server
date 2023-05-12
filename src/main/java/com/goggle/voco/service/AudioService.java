package com.goggle.voco.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AudioService {
    public void audioInput(Long userId, Long textId, MultipartFile audio) throws IOException;
    public ResponseEntity<ByteArrayResource> getAudio(Long userId, Long textId);
}

