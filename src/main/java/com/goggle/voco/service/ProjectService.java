package com.goggle.voco.service;

import com.goggle.voco.dto.ProjectResponseDTO;
import com.goggle.voco.dto.ProjectsResponseDTO;

import java.util.List;

public interface ProjectService {
    ProjectResponseDTO createProject(ProjectResponseDTO projectDTO);
    ProjectsResponseDTO getProjectList();
    ProjectResponseDTO getProjectById(Long projectId);
}
