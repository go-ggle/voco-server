package com.goggle.voco.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //401 UNAUTHORIZED
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    //400 BAD REQUEST
    INVALID_USER(HttpStatus.BAD_REQUEST, "유효하지 않은 유저입니다."),
    ALREADY_JOINED(HttpStatus.BAD_REQUEST, "이미 속해있는 팀입니다."),

    //404 NOT_FOUND
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 팀이 존재하지 않습니다."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 프로젝트가 존재하지 않습니다."),
    BLOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 블럭이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
