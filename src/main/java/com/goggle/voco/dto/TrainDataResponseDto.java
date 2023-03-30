package com.goggle.voco.dto;

import com.goggle.voco.domain.Block;
import com.goggle.voco.domain.Train;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainDataResponseDto {

    private Long id;
    private int text_id;
    private String text;

    public static TrainDataResponseDto from(Train train) {
        return new TrainDataResponseDto(
                train.getId(),
                train.getTextId(),
                train.getText()
        );
    }
}
