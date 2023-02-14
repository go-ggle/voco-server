package com.goggle.voco.service;

import com.goggle.voco.domain.Project;
import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;
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
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDTO) {
        Project project = new Project(projectRequestDTO.getLanguage(), projectRequestDTO.getTitle());
        projectRepository.save(project);

        return ProjectResponseDto.from(project);
    }

    @Override
    public ProjectsResponseDto findProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponseDto> projectResponseDtos = projects.stream()
                .map(project -> ProjectResponseDto.from(project))
                .collect(Collectors.toList());

        return new ProjectsResponseDto(projectResponseDtos);
    }

    @Override
    public ProjectResponseDto findProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);

        if (project.isPresent()) {
           return ProjectResponseDto.from(project.get());
        }
        return null;
    }
}
