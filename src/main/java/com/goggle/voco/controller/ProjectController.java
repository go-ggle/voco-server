package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.ProjectRequestDto;
import com.goggle.voco.dto.ProjectResponseDto;
import com.goggle.voco.dto.ProjectsResponseDto;
import com.goggle.voco.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/teams/{teamId}/projects")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Project", description = "프로젝트 API")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("")
    @Operation(summary = "프로젝트 생성", description = "프로젝트를 생성한다.")
    public ResponseEntity<ProjectResponseDto> createProject(
            @AuthenticationPrincipal User user,
            @RequestBody ProjectRequestDto projectRequestDto,
            @PathVariable("teamId") Long teamId) {

        ProjectResponseDto projectResponseDto = projectService.createProject(projectRequestDto, teamId);

        return new ResponseEntity<>(projectResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "프로젝트 목록 조회", description = "팀 프로젝트 목록을 조회한다.")
    public ResponseEntity<ProjectsResponseDto> findProjects(
            @AuthenticationPrincipal User user,
            @PathVariable("teamId") Long teamId) {
        ProjectsResponseDto projectsResponseDto = projectService.findProjects(teamId);

        return new ResponseEntity<>(projectsResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "프로젝트 상세 조회", description = "팀 프로젝트 상세 내용을 조회한다.")
    public ResponseEntity<ProjectResponseDto> findProjectById(@PathVariable("projectId") Long projectId) {
        ProjectResponseDto projectResponseDto = projectService.findProjectById(projectId);

        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "프로젝트 삭제", description = "특정 팀 프로젝트를 삭제한다.")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId) throws Exception {
        projectService.deleteProject(projectId);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> updateProjectTitle(@PathVariable("projectId") Long projectId, @RequestBody Map<String, String> body) throws Exception {
        ProjectResponseDto projectResponseDto = projectService.updateProjectTitle(projectId, body.get("title"));

        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);
    }
}
