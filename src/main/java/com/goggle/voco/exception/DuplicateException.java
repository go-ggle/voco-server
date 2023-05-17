package com.goggle.voco.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends CustomException{

    public DuplicateException(ErrorCode errorCode) {

        super(HttpStatus.CONFLICT, errorCode.getMessage());
    }
}
