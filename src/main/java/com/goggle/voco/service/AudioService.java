package com.goggle.voco.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AudioInputService{
    public void audioInput(Long userId, Long textId, MultipartFile audio) throws IOException;
}

