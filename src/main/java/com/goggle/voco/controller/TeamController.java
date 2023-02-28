package com.goggle.voco.controller;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TeamRequestDto;
import com.goggle.voco.dto.TeamResponseDto;
import com.goggle.voco.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teams")
@Log4j2
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("")
    public ResponseEntity<TeamResponseDto> createTeam(
            @AuthenticationPrincipal User user,
            @RequestBody TeamRequestDto teamRequestDto) throws Exception {
        TeamResponseDto teamResponseDto = teamService.createTeam(user, teamRequestDto);

        return new ResponseEntity<>(teamResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/{teamCode}")
    public ResponseEntity<TeamResponseDto> joinTeam(
            @AuthenticationPrincipal User user,
            @PathVariable("teamCode") String teamCode) throws Exception {
        TeamResponseDto teamResponseDto = teamService.joinTeam(user, teamCode);

        return new ResponseEntity<>(teamResponseDto, HttpStatus.CREATED);
    }
}
