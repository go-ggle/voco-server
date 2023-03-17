package com.goggle.voco.service;

import com.goggle.voco.config.security.JwtTokenProvider;
import com.goggle.voco.domain.Participation;
import com.goggle.voco.domain.Team;
import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TokenRequestDto;
import com.goggle.voco.dto.TokenResponseDto;
import com.goggle.voco.dto.UserRequestDto;
import com.goggle.voco.exception.BadRequestException;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.repository.ParticipationRepository;
import com.goggle.voco.repository.TeamRepository;
import com.goggle.voco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ParticipationRepository participationRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRequestDto userRequestDto) {
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        User user = new User(userRequestDto.getEmail(), userRequestDto.getNickname(), password);
        userRepository.save(user);

        Team team = new Team(user, true);
        teamRepository.save(team);

        Participation participation = new Participation(user, team);
        participationRepository.save(participation);

        return user;
    }

    @Override
    public TokenResponseDto createToken(TokenRequestDto tokenRequestDto) {
        User user = userRepository.findByEmail(tokenRequestDto.getEmail()).orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_USER));

        if (!passwordEncoder.matches(tokenRequestDto.getPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorCode.INVALID_USER);
        }

        String accessToken = jwtTokenProvider.createToken(String.valueOf(user.getId()));

        return new TokenResponseDto(accessToken);
    }
}
