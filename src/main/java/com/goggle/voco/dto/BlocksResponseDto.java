package com.goggle.voco.dto;

import com.goggle.voco.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlocksResponseDto {
    private List<BlockResponseDto> blocks;
}
