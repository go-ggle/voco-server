package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TeamRequestDto;
import com.goggle.voco.dto.TeamResponseDto;
import com.goggle.voco.dto.VoiceResponseDto;

import java.util.List;

public interface TeamService {
    TeamResponseDto createTeam(User user, TeamRequestDto teamRequestDto) throws Exception;
    List<TeamResponseDto> findTeams(User user) throws Exception;
    TeamResponseDto joinTeam(User user, String teamCode) throws Exception;
    List<VoiceResponseDto> findRegisteredTeamMembers(Long teamId) throws Exception;
}
