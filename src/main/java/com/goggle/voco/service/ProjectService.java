package com.goggle.voco.service;

import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;

public interface ProjectService {
    ProjectResponseDto createProject(ProjectRequestDto projectRequestDTO);
    ProjectsResponseDto findProjects();
    ProjectResponseDto findProjectById(Long projectId);
}
