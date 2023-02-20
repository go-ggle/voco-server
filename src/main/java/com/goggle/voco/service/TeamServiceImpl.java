package com.goggle.voco.service;

import com.goggle.voco.dto.TeamRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    @Override
    public TeamRequestDto createTeam(TeamRequestDto teamRequestDto) {
        return null;
    }
}
