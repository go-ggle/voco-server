package com.goggle.voco.domain.train.dto;

import com.goggle.voco.domain.train.domain.Train;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainDatumResponseDto {

    private int textId;
    private String text;

    public static TrainDatumResponseDto from(Train train) {
        return new TrainDatumResponseDto(
                train.getTextId(),
                train.getText()
        );
    }
}