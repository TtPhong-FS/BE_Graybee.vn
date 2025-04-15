package vn.graybee.serviceImps.others;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RedisServices {

    private final String TOKEN_KEY_PREFIX = "token:user:";

    private final StringRedisTemplate redisTemplate;

    public RedisServices(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(Integer userUid, String token, long duration) {
        String tokenKey = TOKEN_KEY_PREFIX + userUid;
        Long expireAt = Instant.now().plusSeconds(duration).getEpochSecond();

        redisTemplate.delete(tokenKey);

        redisTemplate.opsForHash().put(tokenKey, "token", token);

        redisTemplate.opsForHash().put(tokenKey, "expiryTime", String.valueOf(expireAt));

        redisTemplate.opsForHash().put(tokenKey, "status_logout", "false");
    }

    public void cleanupTokenExpired(Integer userUid) {
        String key = TOKEN_KEY_PREFIX + userUid;
        long now = Instant.now().getEpochSecond();

        Object expiredToken = redisTemplate.opsForHash().get(key, "expiryTime");
        String status = (String) redisTemplate.opsForHash().get(key, "status_logout");

        if (expiredToken != null) {
            long expiryTime = Long.parseLong(expiredToken.toString());
            boolean isLoggedOut = "true".equals(status != null ? status : "false");

            if (expiryTime <= now || isLoggedOut) {
                redisTemplate.delete(key);
            }
        }
    }

    public boolean isValidToken(Integer userUid, String token) {
        String key = TOKEN_KEY_PREFIX + userUid;

        String status = (String) redisTemplate.opsForHash().get(key, "status_logout");
        Object storedToken = redisTemplate.opsForHash().get(key, "token");

        if (storedToken == null || !storedToken.equals(token)) {
            return false;
        }
        boolean isLoggedOut = "true".equals(status != null ? status : "false");
        return !isLoggedOut;
    }

    public void updateStatusToken(Integer userUid) {
        String key = TOKEN_KEY_PREFIX + userUid;

        String status = (String) redisTemplate.opsForHash().get(key, "status_logout");
        if (status != null) {
            redisTemplate.opsForHash().put(key, "status_logout", "true");
        }
    }

}
