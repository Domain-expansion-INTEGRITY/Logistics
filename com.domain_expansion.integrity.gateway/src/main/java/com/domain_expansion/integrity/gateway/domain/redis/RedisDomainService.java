package com.domain_expansion.integrity.gateway.domain.redis;

import com.domain_expansion.integrity.gateway.domain.dto.UserAuthDto;
import com.domain_expansion.integrity.gateway.presentation.filter.JwtFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisDomainService {

    private final RedisTemplate<String, UserAuthDto> redisTemplate;
    private final ObjectMapper objectMapper;

    public void setUserData(UserAuthDto authDto) {

        ValueOperations<String, UserAuthDto> ops =
            redisTemplate.opsForValue();

        ops.set(UserAuthDto.getRedisKey(authDto.userId()), authDto, JwtFilter.TOKEN_TIME,
            TimeUnit.MILLISECONDS);

    }

    public UserAuthDto findUserAuth(Long userId) {
        ValueOperations<String, UserAuthDto> ops =
            redisTemplate.opsForValue();

        return objectMapper.convertValue(ops.get(UserAuthDto.getRedisKey(userId)),
            UserAuthDto.class);
    }

    public void deleteUserData(String username) {
        redisTemplate.delete(username);
    }
}
