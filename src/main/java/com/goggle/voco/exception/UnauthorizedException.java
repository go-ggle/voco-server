package com.goggle.voco.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException{
    public UnauthorizedException(ErrorCode errorCode) {

        super(HttpStatus.UNAUTHORIZED, errorCode.getMessage());
    }
}
