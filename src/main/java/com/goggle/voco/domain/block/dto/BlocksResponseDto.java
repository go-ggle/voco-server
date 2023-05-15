package com.goggle.voco.domain.block.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlocksResponseDto {
    private List<BlockResponseDto> blocks;
}
