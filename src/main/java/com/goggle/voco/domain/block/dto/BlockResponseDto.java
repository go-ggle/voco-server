package com.goggle.voco.domain.block.dto;

import com.goggle.voco.domain.block.domain.Block;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


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
            block.getVoiceId(),
            block.getInterval(),
            block.getOrder()
        );
    }
}
