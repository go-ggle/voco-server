package com.goggle.voco.dto;

import com.goggle.voco.domain.Block;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlockResponseDto {

    private Long id;
    private String text;
    private String audioPath;
    private LocalDateTime createdAt, updatedAt;

    public static BlockResponseDto from(Block block) {
        return new BlockResponseDto(
            block.getId(),
            block.getText(),
            block.getAudioPath(),
            block.getCreatedAt(),
            block.getUpdatedAt()
        );
    }
}
