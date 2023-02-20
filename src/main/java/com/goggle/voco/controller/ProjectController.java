package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;
import com.goggle.voco.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/projects")
@Log4j2
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<ProjectResponseDto> createProject(
            @AuthenticationPrincipal User user,
            @RequestBody ProjectRequestDto projectRequestDto) {

        ProjectResponseDto projectResponseDto = projectService.createProject(projectRequestDto, user);

        return new ResponseEntity<>(projectResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ProjectsResponseDto> findProjects(@AuthenticationPrincipal User user) {
        ProjectsResponseDto projectsResponseDto = projectService.findProjects(user);

        return new ResponseEntity<>(projectsResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> findProjectById(@PathVariable("projectId") Long projectId) {
        ProjectResponseDto projectResponseDto = projectService.findProjectById(projectId);

        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId) throws Exception {
        projectService.deleteProject(projectId);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
