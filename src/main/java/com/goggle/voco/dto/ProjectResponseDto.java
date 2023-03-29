package com.goggle.voco.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goggle.voco.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectResponseDto {

    private Long id;
    private Long team;
    private String title;
    private Long language;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime updatedAt;

    private boolean isBookmarked;

    public static ProjectResponseDto from(Project project) {
        return new ProjectResponseDto(
                project.getId(),
                project.getTeam().getId(),
                project.getTitle(),
                project.getLanguage(),
                project.getUpdatedAt(),
                false
        );
    }
}