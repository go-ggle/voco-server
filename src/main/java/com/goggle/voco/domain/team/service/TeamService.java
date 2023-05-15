package com.goggle.voco.domain.team.service;

import com.goggle.voco.domain.team.dto.TeamRequestDto;
import com.goggle.voco.domain.team.dto.TeamResponseDto;
import com.goggle.voco.domain.user.dto.VoiceResponseDto;

import java.util.List;

public interface TeamService {
    TeamResponseDto createTeam(Long userId, TeamRequestDto teamRequestDto) throws Exception;
    List<TeamResponseDto> findTeams(Long userId) throws Exception;
    TeamResponseDto joinTeam(Long userId, String teamCode) throws Exception;
    List<VoiceResponseDto> findRegisteredTeamMembers(Long teamId) throws Exception;
}
