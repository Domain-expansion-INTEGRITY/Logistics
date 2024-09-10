package com.domain_expansion.integrity.auth.domain.redis;

import com.domain_expansion.integrity.auth.common.jwt.JwtUtils;
import com.domain_expansion.integrity.auth.domain.dto.UserAuthDto;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisDomainService {

    private final RedisTemplate<String, UserAuthDto> redisTemplate;

    public void setUserData(UserAuthDto authDto) {

        ValueOperations<String, UserAuthDto> ops =
            redisTemplate.opsForValue();

        ops.set(authDto.username(), authDto, JwtUtils.TOKEN_TIME, TimeUnit.MILLISECONDS);

    }

    public UserAuthDto findUserAuth(String username) {
        ValueOperations<String, UserAuthDto> ops =
            redisTemplate.opsForValue();

        return ops.get(username);
    }

    public void deleteUserData(String username) {
        redisTemplate.delete(username);
    }
}
