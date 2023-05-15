package com.goggle.voco.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long voiceId;
    private Long interval;
    private Long order;

    public static BlockResponseDto from(Block block) {
        return new BlockResponseDto(
            block.getId(),
            block.getText(),
            block.getAudioPath(),
            block.getInterval(),
            block.getVoiceId(),
            block.getOrder()
        );
    }
}
