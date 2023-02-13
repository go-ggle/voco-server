package com.goggle.voco.controller;

import com.goggle.voco.dto.ProjectRequestDTO;
import com.goggle.voco.dto.ProjectResponseDTO;
import com.goggle.voco.dto.ProjectsResponseDTO;
import com.goggle.voco.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/projects")
@Log4j2
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO projectResponseDTO = projectService.createProject(projectRequestDTO);

        return new ResponseEntity<>(projectResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ProjectsResponseDTO> findProjects() {
        ProjectsResponseDTO projectsResponseDTO = projectService.findProjects();

        return new ResponseEntity<>(projectsResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> findProjectById(@PathVariable("projectId") Long projectId) {
        ProjectResponseDTO projectResponseDTO = projectService.findProjectById(projectId);

        return new ResponseEntity<>(projectResponseDTO, HttpStatus.OK);
    }
}
