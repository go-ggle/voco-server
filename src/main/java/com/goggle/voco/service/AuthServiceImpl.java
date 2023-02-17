package com.goggle.voco.service;

import com.goggle.voco.domain.User;
import com.goggle.voco.dto.TokenRequestDto;
import com.goggle.voco.dto.UserRequestDto;
import com.goggle.voco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public User createUser(UserRequestDto userRequestDto) {
        log.info(userRequestDto);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        User user = new User(userRequestDto.getEmail(), userRequestDto.getNickname(), password);
        userRepository.save(user);

        return user;
    }

    @Override
    public String createToken(TokenRequestDto tokenRequestDto) {
        User user = userRepository.getById(tokenRequestDto.getEmail());

        String accessToken =

        return null;
    }
}
