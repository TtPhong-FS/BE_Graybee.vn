package vn.graybee.checkers;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
@Component
public class StartupConnectionChecker implements ApplicationListener<ApplicationReadyEvent> {

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
                throw new SQLException("Invalid Mysql Connect");
            }
            System.out.println(message_success + "Mysql");
        } catch (SQLException e) {
            throw new IllegalStateException("Mysql error: " + e.getMessage());
        }
    }

    public void checkRedisConnect() {
        try {
            RedisConnection con = redisConnect.getConnection();
            if (con.isClosed()) {
                System.out.println("Redis closed");
            }
            System.out.println(message_success + "Redis");
        } catch (Exception e) {
            throw new IllegalStateException("Redis error: " + e.getMessage());
        }
    }


}
