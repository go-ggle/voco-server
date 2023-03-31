package com.goggle.voco.service;

import com.goggle.voco.domain.Bookmark;
import com.goggle.voco.domain.Project;
import com.goggle.voco.domain.Team;
import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.repository.BookmarkRepository;
import com.goggle.voco.repository.ProjectRepository;
import com.goggle.voco.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class    ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto, Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND));
        Project project = new Project(projectRequestDto.getLanguage(), projectRequestDto.getTitle(), team);
        projectRepository.save(project);

        return ProjectResponseDto.from(project, false);
    }

    @Override
    public ProjectsResponseDto findProjects(Long userId, Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND));

        List<Project> projects = projectRepository.findByTeamIdOrderByUpdatedAtDesc(teamId);
        List<Long> bookmarkedProjectIds = bookmarkRepository.findByUserIdAndTeamId(userId, teamId)
                .stream()
                .map(Bookmark::getProjectId)
                .collect(Collectors.toList());
        List<ProjectResponseDto> projectResponseDtos = projects.stream()
                .map(project -> ProjectResponseDto.from(project, bookmarkedProjectIds.contains(project.getId())))
                .collect(Collectors.toList());

        return new ProjectsResponseDto(projectResponseDtos);
    }

    @Override
    public ProjectResponseDto findProjectById(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        boolean isBookmarked = bookmarkRepository.existsByUser_IdAndProject_Id(userId, projectId);

        return ProjectResponseDto.from(project, isBookmarked);
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        projectRepository.delete(project);
    }

    @Override
    public ProjectResponseDto updateProjectTitle(Long userId, Long projectId, String title) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        project.setTitle(title);
        project.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(project);

        boolean isBookmarked = bookmarkRepository.existsByUser_IdAndProject_Id(userId, projectId);

        return ProjectResponseDto.from(project, isBookmarked);
    }
}
