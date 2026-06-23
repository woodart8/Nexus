package com.gentle.nexus.auth.service;

import com.gentle.nexus.common.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, String> redisTemplate;

    public void save(Long userId, String refreshToken) {
        redisTemplate.opsForValue()
                .set(jwtProperties.getPrefix() + userId, refreshToken, Duration.ofDays(7));
    }

    public String get(Long userId) {
        return redisTemplate.opsForValue().get(jwtProperties.getPrefix() + userId);
    }

    public void rotate(Long userId, String refreshToken) {
        redisTemplate.opsForValue().set(jwtProperties.getPrefix() + userId, refreshToken, Duration.ofDays(7));
    }

    public void delete(Long userId) {
        redisTemplate.delete(jwtProperties.getPrefix() + userId);
    }
}