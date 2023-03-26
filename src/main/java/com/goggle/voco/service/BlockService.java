package com.goggle.voco.service;

import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;
import com.goggle.voco.dto.BlocksResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;

public interface BlockService {
    String createAudio(AudioRequestDto audioRequestDto);
    void mergeBlocks(Long projectId);
    BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long projectId);
    BlocksResponseDto findBlocks(Long projectId);
    void deleteBlock(Long blockId) throws Exception;
    BlockResponseDto updateBlock(AudioRequestDto audioRequestDto, Long blockId) throws Exception;
}
