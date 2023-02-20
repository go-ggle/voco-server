package com.goggle.voco.service;

import com.goggle.voco.domain.Team;
import com.goggle.voco.dto.TeamRequestDto;
import com.goggle.voco.dto.TeamResponseDto;
import com.goggle.voco.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public TeamResponseDto createTeam(TeamRequestDto teamRequestDto) {
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int alphaNumLength = alphaNum.length();

        Random random = new Random();

        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 7; i++) {
            code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
        }
        String teamCode = code.toString();

        Team team = new Team(teamRequestDto.getName(), teamCode);
        teamRepository.save(team);

        return TeamResponseDto.from(team);
    }
}
