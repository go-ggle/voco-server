package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.AudioInputRequestDto;
import com.goggle.voco.dto.AudioInputResponseDto;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface AudioInputService{
    public AudioInputResponseDto audioInput(User user, Long textId, MultipartFile audio) throws IOException;
}

