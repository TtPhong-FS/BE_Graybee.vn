package vn.graybee.controllers.admin;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/redis")
public class AdminRedisController {

    private final StringRedisTemplate redisTemplate;

    public AdminRedisController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/hello-redis")
    public String testRedis() {
        redisTemplate.opsForValue().set("test", "Hello-redis");
        return redisTemplate.opsForValue().get("test");
    }

}
