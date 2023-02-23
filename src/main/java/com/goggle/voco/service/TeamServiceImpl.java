package com.goggle.voco.service;

import com.goggle.voco.domain.Team;
import com.goggle.voco.domain.User;
import com.goggle.voco.domain.User_Team;
import com.goggle.voco.dto.TeamRequestDto;
import com.goggle.voco.dto.TeamResponseDto;
import com.goggle.voco.repository.TeamRepository;
import com.goggle.voco.repository.UserRepository;
import com.goggle.voco.repository.UserTeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final UserTeamRepository userTeamRepository;

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
    public TeamResponseDto createTeam(TeamRequestDto teamRequestDto) {
        String teamCode = createCode();
        Team team = new Team(teamRequestDto.getName(), teamCode);
        teamRepository.save(team);

        Optional<Team> selectedTeam = teamRepository.findByTeamCode(teamCode);
        Optional<User> selectedUser = userRepository.findById(teamRequestDto.getUserId());

        User_Team userTeam = new User_Team();
        if(selectedTeam.isPresent() && selectedUser.isPresent()){
            Team createdTeam = selectedTeam.get();
            User user = selectedUser.get();

            userTeam.setTeam(createdTeam);
            userTeam.setUser(user);

            userTeamRepository.save(userTeam);
        }
        return TeamResponseDto.from(team);
    }
}
