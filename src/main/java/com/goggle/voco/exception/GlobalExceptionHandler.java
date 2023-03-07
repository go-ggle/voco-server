package com.goggle.voco.exception;

import com.goggle.voco.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler({ CustomException.class })
    protected ResponseEntity handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponseDto(e.getHttpStatus().value(), e.getHttpStatus().name(), e.getMessage()));
    }
}
