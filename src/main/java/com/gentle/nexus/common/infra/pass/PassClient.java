package com.gentle.nexus.common.infra.pass;

import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import com.gentle.nexus.common.infra.pass.dto.PassRequestDto;
import com.gentle.nexus.common.infra.pass.dto.PassResponseDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class PassClient {

    private final RestClient restClient;

    @Value("${external.pass-url}")
    private String passUrl;

    @Retry(name = "passApi")
    @CircuitBreaker(name = "passApi", fallbackMethod = "fallback")
    public PassResponseDto requestCi(PassRequestDto req) {

        return restClient.post()
                .uri(passUrl + "/pass/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .body(req)
                .retrieve()
                .body(PassResponseDto.class);
    }

    private PassResponseDto fallback(PassRequestDto req, Throwable t) {
        throw new BusinessException(ErrorCode.CI_SERVER_UNAVAILABLE);
    }
}
