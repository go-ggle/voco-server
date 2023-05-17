package com.goggle.voco.domain.team.service;

import com.goggle.voco.domain.team.domain.Team;
import com.goggle.voco.domain.user.domain.User;
import com.goggle.voco.domain.participation.domain.Participation;
import com.goggle.voco.domain.team.dto.TeamRequestDto;
import com.goggle.voco.domain.team.dto.TeamResponseDto;
import com.goggle.voco.domain.user.dto.VoiceResponseDto;
import com.goggle.voco.exception.DuplicateException;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.domain.team.repository.TeamRepository;
import com.goggle.voco.domain.user.repository.UserRepository;
import com.goggle.voco.domain.participation.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;

    public String createCode(){
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int alphaNumLength = alphaNum.length();

        Random random = new Random();

        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 7; i++) {
            code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
        }

        return code.toString();
    }

    @Override
    public TeamResponseDto createTeam(Long userId, TeamRequestDto teamRequestDto) {
        String teamCode = createCode();
        Team team = new Team(teamRequestDto.getName(), teamCode);
        teamRepository.save(team);

        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Participation participation = new Participation(user, team);
        participationRepository.save(participation);

        return TeamResponseDto.from(team);
    }

    @Override
    public List<TeamResponseDto> findTeams(Long userId) {
        Team privateTeam = participationRepository.findPrivateTeamByUserId(userId);
        List<Team> publicTeams = participationRepository.findTeamsByUserId(userId);

        List<Team> teams = new ArrayList<>();
        teams.add(privateTeam);
        teams.addAll(publicTeams);

        List<TeamResponseDto> teamResponseDtos = teams.stream()
                .map(team -> TeamResponseDto.from(team))
                .collect(Collectors.toList());

        return teamResponseDtos;
    }

    @Override
    public TeamResponseDto joinTeam(Long userId, String teamCode) {
        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException(ErrorCode.USER_NOT_FOUND));
        Team team = teamRepository.findByTeamCode(teamCode).orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND));

        if(participationRepository.existsByUserAndTeam(user, team)) {
            throw new DuplicateException(ErrorCode.PARICIPATION_CONFLICT);
        }

        Participation participation = new Participation(user, team);
        participationRepository.save(participation);

        return TeamResponseDto.from(team);
    }

    @Override
    public List<VoiceResponseDto> findRegisteredTeamMembers(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(()-> new NotFoundException(ErrorCode.TEAM_NOT_FOUND));
        List<User> registeredUsers = participationRepository.findRegisteredUsersByTeamId(teamId);

        List<VoiceResponseDto> voiceResponseDtos = registeredUsers.stream()
                .map(user -> VoiceResponseDto.from(user))
                .collect(Collectors.toList());

        return voiceResponseDtos;
    }
}
