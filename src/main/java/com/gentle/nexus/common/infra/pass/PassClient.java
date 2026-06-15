package com.gentle.nexus.common.infra.pass;

import com.gentle.nexus.common.infra.pass.dto.PassRequestDto;
import com.gentle.nexus.common.infra.pass.dto.PassResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class PassClient {

    private final RestClient restClient;

    @Value("${external.pass-url}")
    private String passUrl;

    public PassClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public PassResponseDto requestCi(PassRequestDto passRequestDto) {
        return restClient.post()
                // 💡 .uri() 내부에 상대 경로가 아닌, yml에서 꺼내온 전체 URL을 입력합니다.
                .uri(passUrl + "/pass/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .body(passRequestDto)
                .retrieve()
                .body(PassResponseDto.class);
    }

}
