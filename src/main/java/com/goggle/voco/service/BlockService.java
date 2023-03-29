package com.goggle.voco.service;

import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;
import com.goggle.voco.dto.BlocksResponseDto;

public interface BlockService {
    String createAudio(AudioRequestDto audioRequestDto, Long teamId);
    void mergeBlocks(Long teamId, Long projectId);
    BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long teamId, Long projectId);
    BlocksResponseDto findBlocks(Long projectId);
    void deleteBlock(Long projectId, Long teamId, Long blockId) throws Exception;
    BlockResponseDto updateBlock(AudioRequestDto audioRequestDto, Long teamId, Long blockId) throws Exception;
}
