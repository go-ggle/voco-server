package com.goggle.voco.service;

import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;

public interface BlockService {
    String createAudio(AudioRequestDto audioRequestDto);
    BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long projectId);
}
