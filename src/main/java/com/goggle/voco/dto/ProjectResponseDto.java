package com.goggle.voco.dto;

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
    private String title;
    private String language;
    private Long user;

    private LocalDateTime createdAt, updatedAt;
    public static ProjectResponseDto from(Project project) {
        return new ProjectResponseDto(
                project.getId(),
                project.getTitle(),
                project.getLanguage(),
                project.getTeam().getId(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}