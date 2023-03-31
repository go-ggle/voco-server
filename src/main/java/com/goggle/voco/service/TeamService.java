package com.goggle.voco.service;

import com.goggle.voco.dto.TeamRequestDto;
import com.goggle.voco.dto.TeamResponseDto;
import com.goggle.voco.dto.VoiceResponseDto;

import java.util.List;

public interface TeamService {
    TeamResponseDto createTeam(Long userId, TeamRequestDto teamRequestDto) throws Exception;
    List<TeamResponseDto> findTeams(Long userId) throws Exception;
    TeamResponseDto joinTeam(Long userId, String teamCode) throws Exception;
    List<VoiceResponseDto> findRegisteredTeamMembers(Long teamId) throws Exception;
}
