package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TeamRequestDto;
import com.goggle.voco.dto.TeamResponseDto;
import com.goggle.voco.dto.TeamsResponseDto;

public interface TeamService {
    TeamResponseDto createTeam(User user, TeamRequestDto teamRequestDto) throws Exception;
    TeamsResponseDto findTeams(User user) throws Exception;
    TeamResponseDto joinTeam(User user, String teamCode) throws Exception;
}
