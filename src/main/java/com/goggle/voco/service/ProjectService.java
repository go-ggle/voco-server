package com.goggle.voco.service;

import com.goggle.voco.dto.ProjectRequestDTO;
import com.goggle.voco.dto.ProjectResponseDTO;
import com.goggle.voco.dto.ProjectsResponseDTO;

import java.util.List;

public interface ProjectService {
    ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO);
    ProjectsResponseDTO findProjects();
    ProjectResponseDTO findProjectById(Long projectId);
}
