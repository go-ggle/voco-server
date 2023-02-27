package com.goggle.voco.service;

import com.goggle.voco.dto.AudioRequestDto;
import com.goggle.voco.dto.BlockResponseDto;
import com.goggle.voco.dto.BlocksResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;

public interface BlockService {
    String createAudio(AudioRequestDto audioRequestDto);
    BlockResponseDto createBlock(AudioRequestDto audioRequestDto, Long projectId);
    BlocksResponseDto findBlocks(Long teamId);
}
