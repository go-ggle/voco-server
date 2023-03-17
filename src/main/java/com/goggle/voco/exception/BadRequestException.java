package com.goggle.voco.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException{
    public BadRequestException(ErrorCode errorCode) {

        super(HttpStatus.BAD_REQUEST, errorCode.getMessage());
    }
}
