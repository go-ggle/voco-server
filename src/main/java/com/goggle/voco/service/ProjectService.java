package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;

public interface ProjectService {
    ProjectResponseDto createProject(ProjectRequestDto projectRequestDTO, Long teamId);
    ProjectsResponseDto findProjects(User user, Long teamId);
    ProjectResponseDto findProjectById(User user, Long projectId);
    void deleteProject(Long projectId) throws Exception;
    ProjectResponseDto updateProjectTitle(User user, Long projectId, String title) throws Exception;
}
