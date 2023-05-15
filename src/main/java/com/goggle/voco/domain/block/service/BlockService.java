package com.goggle.voco.domain.block.service;

import com.goggle.voco.domain.block.dto.AudioRequestDto;
import com.goggle.voco.domain.block.dto.BlockCreateRequestDto;
import com.goggle.voco.domain.block.dto.BlockResponseDto;
import com.goggle.voco.domain.block.dto.BlocksResponseDto;

public interface BlockService {
    String createAudio(AudioRequestDto audioRequestDto, Long teamId, Long projectId, Long blockId);
    void mergeBlocks(Long teamId, Long projectId);
    void increaseOrders(Long projectId, Long order);
    void decreaseOrders(Long projectId, Long order);
    BlockResponseDto createBlock(BlockCreateRequestDto blockCreateRequestDto, Long teamId, Long projectId);
    BlocksResponseDto findBlocks(Long projectId);
    void deleteBlock(Long projectId, Long teamId, Long blockId) throws Exception;
    BlockResponseDto updateBlock(AudioRequestDto audioRequestDto, Long teamId, Long projectId, Long blockId) throws Exception;
}
