package vn.graybee.services.auth;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisAuthServices {

    private static final String TOKEN_USER_KEY = "token:user:";

    private final StringRedisTemplate redisTemplate;

    public RedisAuthServices(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(Integer userUid, String token, long timeout, TimeUnit unit) {
        String tokenKey = TOKEN_USER_KEY + userUid;

        redisTemplate.delete(tokenKey);

        redisTemplate.opsForHash().put(tokenKey, "token", token);

        redisTemplate.opsForHash().put(tokenKey, "status_logout", "false");

        redisTemplate.expire(tokenKey, timeout, unit);
    }

    public boolean isValidToken(Integer userUid, String token) {
        String key = TOKEN_USER_KEY + userUid;

        String status = (String) redisTemplate.opsForHash().get(key, "status_logout");
        Object storedToken = redisTemplate.opsForHash().get(key, "token");

        if (storedToken == null || !storedToken.equals(token)) {
            return false;
        }
        boolean isLoggedOut = "true".equals(status != null ? status : "false");
        return !isLoggedOut;
    }

    public void updateStatusToken(Integer userUid) {
        String key = TOKEN_USER_KEY + userUid;

        String status = (String) redisTemplate.opsForHash().get(key, "status_logout");
        if (status != null) {
            redisTemplate.opsForHash().put(key, "status_logout", "true");
        }
    }

}
