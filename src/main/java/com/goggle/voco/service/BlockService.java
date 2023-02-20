package com.goggle.voco.service;

import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;

public interface BlockService {
    String createAudio(String text, Long userId);
    BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long projectId);
}
