package com.goggle.voco.service;

import com.goggle.voco.domain.Project;
import com.goggle.voco.domain.Team;
import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.repository.ProjectRepository;
import com.goggle.voco.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class    ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto, Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND));
        Project project = new Project(projectRequestDto.getLanguage(), projectRequestDto.getTitle(), team);
        projectRepository.save(project);

        return ProjectResponseDto.from(project);
    }

    @Override
    public ProjectsResponseDto findProjects(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND));
        List<Project> projects = projectRepository.findByTeamOrderByUpdatedAtDesc(team);
        List<ProjectResponseDto> projectResponseDtos = projects.stream()
                .map(project -> ProjectResponseDto.from(project))
                .collect(Collectors.toList());

        return new ProjectsResponseDto(projectResponseDtos);
    }

    @Override
    public ProjectResponseDto findProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        return ProjectResponseDto.from(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        projectRepository.delete(project);
    }
}
