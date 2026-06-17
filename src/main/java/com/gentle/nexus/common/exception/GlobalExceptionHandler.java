package com.gentle.nexus.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        ErrorResponse response = ErrorResponse.builder()
                .code("INVALID_REQUEST")
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }

}
