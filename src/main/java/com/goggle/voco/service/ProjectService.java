package com.goggle.voco.service;

import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;

public interface ProjectService {
    ProjectResponseDto createProject(ProjectRequestDto projectRequestDTO, Long teamId);
    ProjectsResponseDto findProjects(Long userId, Long teamId);
    ProjectResponseDto findProjectById(Long userId, Long projectId);
    void deleteProject(Long projectId) throws Exception;
    ProjectResponseDto updateProjectTitle(Long userId, Long projectId, String title) throws Exception;
}
