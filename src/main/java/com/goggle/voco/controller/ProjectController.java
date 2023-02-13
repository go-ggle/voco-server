package com.goggle.voco.controller;

import com.goggle.voco.dto.ProjectsResponseDTO;
import com.goggle.voco.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/projects")
@Log4j2
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<ProjectsResponseDTO> getProjectList() {
        ProjectsResponseDTO projectsResponseDTO = projectService.getProjectList();

        return new ResponseEntity<>(projectsResponseDTO, HttpStatus.OK);
    }

}
