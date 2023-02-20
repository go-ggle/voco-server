package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;

public interface ProjectService {
    ProjectResponseDto createProject(ProjectRequestDto projectRequestDTO, User user);
    ProjectsResponseDto findProjects(User user);
    ProjectResponseDto findProjectById(Long projectId);
}
