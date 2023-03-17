package com.goggle.voco.service;

import com.goggle.voco.domain.Team;
import com.goggle.voco.domain.User;
import com.goggle.voco.domain.Participation;
import com.goggle.voco.dto.TeamRequestDto;
import com.goggle.voco.dto.TeamResponseDto;
import com.goggle.voco.dto.TeamsResponseDto;
import com.goggle.voco.exception.BadRequestException;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.repository.TeamRepository;
import com.goggle.voco.repository.UserRepository;
import com.goggle.voco.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public TeamResponseDto createTeam(User user, TeamRequestDto teamRequestDto) throws Exception {
        String teamCode = createCode();
        Team team = new Team(teamRequestDto.getName(), teamCode);
        teamRepository.save(team);

        Optional<Team> selectedTeam = teamRepository.findByTeamCode(teamCode);
        Optional<User> selectedUser = userRepository.findById(user.getId());

        Participation participation = new Participation();
        if(selectedTeam.isPresent() && selectedUser.isPresent()){
            participation.setTeam(selectedTeam.get());
            participation.setUser(selectedUser.get());

            participationRepository.save(participation);
        }
        else{
            throw new Exception();
        }
        return TeamResponseDto.from(team);
    }

    @Override
    public TeamsResponseDto findTeams(User user) {
        List<Team> teams = participationRepository.findTeamsByUserId(user.getId());
        List<TeamResponseDto> teamResponseDtos = teams.stream()
                .map(team -> TeamResponseDto.from(team))
                .collect(Collectors.toList());

        return new TeamsResponseDto(teamResponseDtos);
    }

    @Override
    public TeamResponseDto joinTeam(User user, String teamCode) {
        Team team = teamRepository.findByTeamCode(teamCode).orElseThrow(()-> new NotFoundException(ErrorCode.TEAM_NOT_FOUND));

        Participation participation = new Participation(user, team);
        participationRepository.save(participation);

        return TeamResponseDto.from(team);
    }
}
