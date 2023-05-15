package com.goggle.voco.domain.user.service;

import com.goggle.voco.config.security.JwtTokenProvider;
import com.goggle.voco.domain.participation.domain.Participation;
import com.goggle.voco.domain.team.domain.Team;
import com.goggle.voco.domain.user.domain.User;
import com.goggle.voco.domain.user.dto.KakaoTokenRequestDto;
import com.goggle.voco.domain.user.dto.KakaoUserResponseDto;
import com.goggle.voco.domain.user.dto.TokenResponseDto;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.UnauthorizedException;
import com.goggle.voco.domain.participation.repository.ParticipationRepository;
import com.goggle.voco.domain.team.repository.TeamRepository;
import com.goggle.voco.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class OAuthServiceImpl implements OAuthService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ParticipationRepository participationRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponseDto createKakaoUserToken(KakaoTokenRequestDto kakaoTokenRequestDto) {
        WebClient webClient = WebClient.builder().baseUrl("https://kapi.kakao.com/v1/oidc/userinfo").build();

        KakaoUserResponseDto kakaoUserResponseDto = webClient.get()
                .headers(h -> h.setBearerAuth(kakaoTokenRequestDto.getAccessToken()))
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value()==401,
                        error -> Mono.error(new UnauthorizedException(ErrorCode.INVALID_AUTH_TOKEN)))
                .bodyToMono(KakaoUserResponseDto.class)
                .block();

        User user = userRepository.findBySocialTypeAndSocialId("kakao", kakaoUserResponseDto.getSub()).orElseGet(()->createKakaoUser(kakaoUserResponseDto));

        String accessToken = jwtTokenProvider.createToken(String.valueOf(user.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()));

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new TokenResponseDto(accessToken, refreshToken, user.getPrivateTeamId());
    }

    @Override
    public User createKakaoUser(KakaoUserResponseDto kakaoUserResponseDto) {
        User user = User.builder()
                .socialType("kakao")
                .socialId(kakaoUserResponseDto.getSub())
                .nickname(kakaoUserResponseDto.getNickname())
                .build();
        userRepository.save(user);

        Team team = new Team(user, true);
        teamRepository.save(team);

        Participation participation = new Participation(user, team);
        participationRepository.save(participation);

        user.setPrivateTeamId(team.getId());
        userRepository.save(user);

        return user;
    }
}
