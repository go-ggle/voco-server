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
public class ProjectResponseDTO {

    private Long id;
    private String title;
    private String language;
    private LocalDateTime createdAt, updatedAt;

    public static ProjectResponseDTO getProjectResponseDTO(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getTitle(),
                project.getLanguage(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}