package com.goggle.voco.domain.team.controller;

import com.goggle.voco.domain.team.dto.TeamRequestDto;
import com.goggle.voco.domain.team.dto.TeamResponseDto;
import com.goggle.voco.domain.user.dto.VoiceResponseDto;
import com.goggle.voco.domain.team.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Team", description = "팀 API")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("")
    @Operation(summary = "팀 생성", description = "팀을 생성한다.")
    public ResponseEntity<TeamResponseDto> createTeam(
            @AuthenticationPrincipal Long userId,
            @RequestBody TeamRequestDto teamRequestDto) throws Exception {
        TeamResponseDto teamResponseDto = teamService.createTeam(userId, teamRequestDto);

        return new ResponseEntity<>(teamResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "팀 목록 조회", description = "로그인한 유저가 속한 팀 목록을 조회한다.")
    public ResponseEntity<List> findTeams(@AuthenticationPrincipal Long userId) throws Exception {
        List<TeamResponseDto> teamResponseDtos = teamService.findTeams(userId);

        return new ResponseEntity<>(teamResponseDtos, HttpStatus.OK);
    }

    @PostMapping("/{teamCode}")
    @Operation(summary = "팀 참여", description = "팀 초대코드를 입력해 팀에 참여한다.")
    public ResponseEntity<TeamResponseDto> joinTeam(
            @AuthenticationPrincipal Long userId,
            @PathVariable("teamCode") String teamCode) throws Exception {
        TeamResponseDto teamResponseDto = teamService.joinTeam(userId, teamCode);

        return new ResponseEntity<>(teamResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{teamId}/voices")
    @Operation(summary = "목소리 등록 완료한 팀원 목록 조회", description = "특정 팀에 속한 팀원 중 목소리 등록을 완료한 팀원 목록을 조회한다.")
    public ResponseEntity<List> findRegisteredMembers(
            @AuthenticationPrincipal Long userId,
            @PathVariable("teamId") Long teamId) throws Exception {
        List<VoiceResponseDto> voiceResponseDtos = teamService.findRegisteredTeamMembers(teamId);

        return new ResponseEntity<>(voiceResponseDtos, HttpStatus.OK);
    }
}
