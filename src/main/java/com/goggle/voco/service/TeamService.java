package com.goggle.voco.service;

import com.goggle.voco.dto.TeamRequestDto;
import com.goggle.voco.dto.TeamResponseDto;

public interface TeamService {
    TeamResponseDto createTeam(TeamRequestDto teamRequestDto) throws Exception;
}
