package com.gentle.nexus.auth.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "RT:";

    public RefreshTokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(Long userId, String refreshToken) {
        redisTemplate.opsForValue()
                .set(PREFIX + userId, refreshToken, Duration.ofDays(7));
    }

    public String get(Long userId) {
        return redisTemplate.opsForValue().get(PREFIX + userId);
    }

    public void rotate(Long userId, String refreshToken) {
        redisTemplate.opsForValue().set(PREFIX + userId, refreshToken, Duration.ofDays(7));
    }

    public void delete(Long userId) {
        redisTemplate.delete(PREFIX + userId);
    }
}