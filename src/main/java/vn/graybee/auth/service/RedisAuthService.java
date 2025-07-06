package vn.graybee.auth.service;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisAuthService {

    private static final String TOKEN_USER_KEY = "token:user:";

    private final StringRedisTemplate redisTemplate;

    public RedisAuthService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(String accountUid, String token, long timeout, TimeUnit unit) {
        String tokenKey = TOKEN_USER_KEY + accountUid;

        redisTemplate.delete(tokenKey);

        redisTemplate.opsForHash().put(tokenKey, "token", token);

        redisTemplate.opsForHash().put(tokenKey, "status_logout", "false");

        redisTemplate.expire(tokenKey, timeout, unit);
    }

    public boolean isLogout(String accountUid) {
        String key = TOKEN_USER_KEY + accountUid;

        String status = (String) redisTemplate.opsForHash().get(key, "status_logout");

        return "true".equals(status != null ? status : "false");
    }

    public boolean isExists(String accountUid, String token) {
        String key = TOKEN_USER_KEY + accountUid;

        Object storedToken = redisTemplate.opsForHash().get(key, "token");

        return storedToken != null && storedToken.equals(token);
    }

    public void updateStatusToken(String accountUid) {
        String key = TOKEN_USER_KEY + accountUid;

        String status = (String) redisTemplate.opsForHash().get(key, "status_logout");
        if (status != null) {
            redisTemplate.opsForHash().put(key, "status_logout", "true");
        }
    }

}
