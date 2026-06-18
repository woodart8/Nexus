package com.gentle.nexus.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다. 아이디 또는 비밀번호를 다시 확인해주세요."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "적합하지 않은 요청입니다."),
    INVALID_NEW_PASSWORD(HttpStatus.BAD_REQUEST, "적합하지 않은 새 비밀번호입니다."),
    CI_SERVER_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "본인 인증 서비스에 일시적인 문제가 발생했습니다."),
    VERIFICATION_FAILED(HttpStatus.BAD_REQUEST, "사용자 인증에 실패했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DB_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "데이터베이스 오류가 발생했습니다."),
    DATA_INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST, "데이터 정합성 위반입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다."),
    DUPLICATE_USER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "부적합한 리프레시 토큰입니다."),
    NO_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "리프레시 토큰이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
