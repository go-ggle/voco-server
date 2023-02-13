package com.goggle.voco.service;

import com.goggle.voco.domain.Project;
import com.goggle.voco.dto.ProjectResponseDTO;
import com.goggle.voco.dto.ProjectsResponseDTO;
import com.goggle.voco.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponseDTO createProject(ProjectResponseDTO projectDTO) {
        return null;
    }

    @Override
    public ProjectsResponseDTO getProjectList() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponseDTO> projectResponseDTOS = projects.stream()
                .map(project -> ProjectResponseDTO.getProjectResponseDTO(project))
                .collect(Collectors.toList());

        return new ProjectsResponseDTO(projectResponseDTOS);
    }

    @Override
    public ProjectResponseDTO getProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);

        if (project.isPresent()) {
           return ProjectResponseDTO.getProjectResponseDTO(project.get());
        }
        return null;
    }
}
