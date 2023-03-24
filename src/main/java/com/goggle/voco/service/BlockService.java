package com.goggle.voco.service;

import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;
import com.goggle.voco.dto.BlocksResponseDto;

public interface BlockService {
    String createAudio(AudioRequestDto audioRequestDto);
    BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long projectId, Long id);
    BlocksResponseDto findBlocks(Long teamId);
    void deleteBlock(Long blockId) throws Exception;
    BlockResponseDto updateBlock(AudioRequestDto audioRequestDto, Long teamId, Long blockId) throws Exception;
}
