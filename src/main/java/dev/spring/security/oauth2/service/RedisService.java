package dev.spring.security.oauth2.service;

import dev.spring.security.oauth2.exception.CustomException;
import dev.spring.security.oauth2.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    public String getCode(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String code = valueOperations.get(key);
        if (code == null) throw new CustomException(ErrorCode.NOT_MATCHED_CODE);
        else return code;
    }

    public void setCodeExpire(String key, String value, Long seconds) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireTime = Duration.ofSeconds(seconds);
        valueOperations.set(key, value, expireTime);
    }

    public void deleteCode(String key) {
        stringRedisTemplate.delete(key);
    }
}
