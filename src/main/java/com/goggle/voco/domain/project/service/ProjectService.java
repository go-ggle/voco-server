package com.goggle.voco.domain.project.service;

import com.goggle.voco.domain.project.dto.ProjectRequestDto;
import com.goggle.voco.domain.project.dto.ProjectResponseDto;
import com.goggle.voco.domain.project.dto.ProjectsResponseDto;

public interface ProjectService {
    ProjectResponseDto createProject(ProjectRequestDto projectRequestDTO, Long teamId);
    ProjectsResponseDto findProjects(Long userId, Long teamId);
    ProjectResponseDto findProjectById(Long userId, Long projectId);
    void deleteProject(Long userId, Long projectId) throws Exception;
    ProjectResponseDto updateProjectTitle(Long userId, Long projectId, String title) throws Exception;
}
