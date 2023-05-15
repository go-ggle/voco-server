package com.goggle.voco.domain.block.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlockCreateRequestDto {
    private Long order;
    private Long voiceId;
}
