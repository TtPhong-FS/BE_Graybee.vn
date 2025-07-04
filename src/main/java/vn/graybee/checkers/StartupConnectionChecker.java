package vn.graybee.checkers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
@Component
public class StartupConnectionChecker implements ApplicationListener<ApplicationReadyEvent> {

    static final Logger logger = LoggerFactory.getLogger(StartupConnectionChecker.class);

    private static final String message_success = "Successfully connect to ";

    private final RedisConnectionFactory redisConnect;

    private final DataSource mysqlConnect;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        checkMysqlConnect();
        checkRedisConnect();
    }

    public void checkMysqlConnect() {
        try {
            Connection con = mysqlConnect.getConnection();
            if (!con.isValid(2)) {
                logger.warn("Mysql connect was closed");
            }
            logger.info(message_success + "Mysql");
        } catch (SQLException e) {
            logger.warn("Mysql error: {}", e.getMessage());
        }
    }

    public void checkRedisConnect() {
        try {
            RedisConnection con = redisConnect.getConnection();
            if (con.isClosed()) {
                logger.info("Redis connect was closed");
            }
            logger.info(message_success + "Redis");
        } catch (Exception e) {
            logger.warn("Redis error: {}", e.getMessage());
        }
    }


}
