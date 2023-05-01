package com.goggle.voco.service;

import com.goggle.voco.config.security.JwtTokenProvider;
import com.goggle.voco.domain.Participation;
import com.goggle.voco.domain.Team;
import com.goggle.voco.domain.User;
import com.goggle.voco.dto.KakaoReponseDto;
import com.goggle.voco.dto.TokenResponseDto;
import com.goggle.voco.repository.ParticipationRepository;
import com.goggle.voco.repository.TeamRepository;
import com.goggle.voco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Log4j2
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ParticipationRepository participationRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponseDto createKakaoUserToken(String token) {
        WebClient webClient = WebClient.builder().baseUrl("https://kapi.kakao.com/v1/oidc/userinfo").build();

        KakaoReponseDto response = webClient.get()
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(KakaoReponseDto.class)
                .block();

        User user = userRepository.findBySocialTypeAndSocialId("kakao", response.getId()).orElse(createKakaoUser(token));
        String accessToken = jwtTokenProvider.createToken(String.valueOf(user.getId()));

        return new TokenResponseDto(accessToken, user.getPrivateTeamId());
    }

    @Override
    public User createKakaoUser(String token) {
        WebClient webClient = WebClient.builder().baseUrl("https://kapi.kakao.com/v2/user/me").build();

        KakaoReponseDto response = webClient.get()
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(KakaoReponseDto.class)
                .block();

        User user = User.builder()
                .socialType("kakao")
                .socialId(response.getId())
                .nickname(response.getProperties().getNickname())
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
