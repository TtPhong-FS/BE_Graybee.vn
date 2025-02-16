//package vn.graybee.services.others;
//
//
//import java.time.Instant;
//
//public class RedisServices {
//
//    private final String TOKEN_KEY_PREFIX = "token:user:";
//
//    private final StringRedisTemplate redisTemplate;
//
//    public void saveToken(Long userId, String token, long duration) {
//        String tokenKey = TOKEN_KEY_PREFIX + userId;
//        long expireAt = Instant.now().plusSeconds(duration).getEpochSecond();
//
//        redisTemplate.delete(tokenKey);
//
//        redisTemplate.opsForHash().put(tokenKey, "token", token);
//
//        redisTemplate.opsForHash().put(tokenKey, "expiryTime", String.valueOf(expireAt));
//
//        redisTemplate.opsForHash().put(tokenKey, "status_logout", "false");
//    }
//
//    public void cleanupTokenExpired(Long userId) {
//        String key = TOKEN_KEY_PREFIX + userId;
//        long now = Instant.now().getEpochSecond();
//
//        Object expiredToken = redisTemplate.opsForHash().get(key, "expiryTime");
//        Object status = redisTemplate.opsForHash().get(key, "status_logout");
//
//        if (expiredToken != null) {
//            long expiryTime = Long.parseLong(expiredToken.toString());
//            boolean isLoggedOut = "true".equals(status != null ? status.toString() : "false");
//
//            if (expiryTime <= now || isLoggedOut) {
//                redisTemplate.delete(key);
//            }
//        }
//    }
//
//    public boolean isValidToken(Long userId, String token) {
//        String key = TOKEN_KEY_PREFIX + userId;
//
//        Object statusToken = redisTemplate.opsForHash().get(key, "status_logout");
//        Object storedToken = redisTemplate.opsForHash().get(key, "token");
//
//        if (storedToken == null || !storedToken.equals(token)) {
//            return false;
//        }
//        boolean isLoggedOut = "true".equals(statusToken != null ? statusToken.toString() : "false");
//        return !isLoggedOut;
//    }
//
//    public void updateStatusToken(Long userId ) {
//        String loggedOutKey = TOKEN_KEY_PREFIX + userId;
//
//        Object status = redisTemplate.opsForHash().get(loggedOutKey, "status_logout");
//        if(status != null) {
//            redisTemplate.opsForHash().put(loggedOutKey, "status_logout", "true");
//        }
//    }
//}
