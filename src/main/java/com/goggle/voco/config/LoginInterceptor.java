package com.goggle.voco.config;

import com.goggle.voco.config.security.JwtTokenProvider;
import com.goggle.voco.domain.User;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);

        if(token==null || !jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        return true;
    }
}
