package com.goggle.voco.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {

    private String title;
    private Long language;
    private Long teamId;
}
