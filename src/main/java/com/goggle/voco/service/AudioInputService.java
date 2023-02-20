package com.goggle.voco.service;

import com.goggle.voco.dto.AudioInputRequestDto;
import com.goggle.voco.dto.AudioInputResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AudioInputService{
    public AudioInputResponseDto audioInput(MultipartFile audio, AudioInputRequestDto audioInputRequestDto) throws IOException;
}

