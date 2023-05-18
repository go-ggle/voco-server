package com.goggle.voco.domain.user.service;

import com.goggle.voco.config.security.JwtTokenProvider;
import com.goggle.voco.domain.participation.domain.Participation;
import com.goggle.voco.domain.team.domain.Team;
import com.goggle.voco.domain.user.domain.User;
import com.goggle.voco.domain.user.dto.*;
import com.goggle.voco.exception.DuplicateException;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.exception.UnauthorizedException;
import com.goggle.voco.domain.participation.repository.ParticipationRepository;
import com.goggle.voco.domain.team.repository.TeamRepository;
import com.goggle.voco.domain.user.repository.UserRepository;
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
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if(userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new DuplicateException(ErrorCode.EMAIL_CONFLICT);
        }

        String password = passwordEncoder.encode(userRequestDto.getPassword());

        User user = User.builder()
                .email(userRequestDto.getEmail())
                .nickname(userRequestDto.getNickname())
                .password(password)
                .build();
        userRepository.save(user);

        Team team = new Team(user, true);
        teamRepository.save(team);

        Participation participation = new Participation(user, team);
        participationRepository.save(participation);

        user.setPrivateTeamId(team.getId());
        userRepository.save(user);

        return UserResponseDto.from(user);
    }

    @Override
    public TokenResponseDto createToken(TokenRequestDto tokenRequestDto) {
        User user = userRepository.findByEmail(tokenRequestDto.getEmail()).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(tokenRequestDto.getPassword(), user.getPassword())) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        String accessToken = jwtTokenProvider.createToken(String.valueOf(user.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()));

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new TokenResponseDto(accessToken, refreshToken, user.getPrivateTeamId());
    }

    @Override
    public TokenRenewResponseDto renewToken(TokenRenewRequestDto tokenRenewRequestDto) {
        String refreshToken=tokenRenewRequestDto.getRefreshToken();

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        Long userId = jwtTokenProvider.getUserId(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        String userRefreshToken = user.getRefreshToken();

        if(!refreshToken.equals(userRefreshToken)) {
            throw new UnauthorizedException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        String accessToken = jwtTokenProvider.createToken(String.valueOf(user.getId()));

        return new TokenRenewResponseDto(accessToken);
    }
}
