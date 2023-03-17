package com.goggle.voco.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{

    public NotFoundException(ErrorCode errorCode) {

        super(HttpStatus.NOT_FOUND, errorCode.getMessage());
    }
}
