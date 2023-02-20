package com.goggle.voco.service;

import com.goggle.voco.domain.Project;
import com.goggle.voco.domain.Team;
import com.goggle.voco.domain.User;
import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;
import com.goggle.voco.repository.ProjectRepository;
import com.goggle.voco.repository.TeamRepository;
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
    private final TeamRepository teamRepository;

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto, Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        Project project = new Project(projectRequestDto.getLanguage(), projectRequestDto.getTitle(), team);
        projectRepository.save(project);

        return ProjectResponseDto.from(project);
    }

    @Override
    public ProjectsResponseDto findProjects(Long teamId) {
        List<Project> projects = projectRepository.findByTeamId(teamId);
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

    @Override
    public void deleteProject(Long projectId) throws Exception {
        Optional<Project> selectedProject = projectRepository.findById(projectId);

        if(selectedProject.isPresent()){
            Project project = selectedProject.get();

            projectRepository.delete(project);
        }
        else{
            throw new Exception();
        }
    }
}
