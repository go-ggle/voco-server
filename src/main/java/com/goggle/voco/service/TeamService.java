package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TeamRequestDto;
import com.goggle.voco.dto.TeamResponseDto;

public interface TeamService {
    TeamResponseDto createTeam(User user, TeamRequestDto teamRequestDto) throws Exception;
    TeamResponseDto joinTeam(User user, String teamCode) throws Exception;
}
