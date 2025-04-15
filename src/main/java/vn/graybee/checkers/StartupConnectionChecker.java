//package vn.graybee.checkers;
//
//import org.elasticsearch.client.Request;
//import org.elasticsearch.client.Response;
//import org.elasticsearch.client.RestClient;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@Component
//public class StartupConnectionChecker implements ApplicationListener<ApplicationReadyEvent> {
//
//    private final RedisConnectionFactory redisConnect;
//
//    private final DataSource mysqlConnect;
//
//    private final RestClient elasticConnect;
//
//    public StartupConnectionChecker(RedisConnectionFactory redisConnect, DataSource mysqlConnect, RestClient elasticConnect) {
//        this.redisConnect = redisConnect;
//        this.mysqlConnect = mysqlConnect;
//        this.elasticConnect = elasticConnect;
//    }
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        checkMysqlConnect();
//        checkRedisConnect();
//        checkElasticConnect();
//    }
//
//    public void checkMysqlConnect() {
//        try {
//            Connection con = mysqlConnect.getConnection();
//            if (!con.isValid(2)) {
//                throw new SQLException("Invalid Mysql Connect");
//            }
//            System.out.println("Mysql already");
//        } catch (SQLException e) {
//            throw new IllegalStateException("Mysql error: " + e.getMessage());
//        }
//    }
//
//    public void checkRedisConnect() {
//        try {
//            RedisConnection con = redisConnect.getConnection();
//            if (con.isClosed()) {
//                System.out.println("Redis closed");
//            }
//            System.out.println("Redis already");
//
//        } catch (Exception e) {
//            throw new IllegalStateException("Redis error: " + e.getMessage());
//        }
//    }
//
//    public void checkElasticConnect() {
//        try {
//            Response con = elasticConnect.performRequest(new Request("GET", "/"));
//            if (con.getStatusLine().getStatusCode() == 200) {
//                System.out.println("ElasticSearch already");
//            } else {
//                System.out.println("Unexpected ElasticSearch status");
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException("ElasticSearch error: " + e.getMessage());
//        }
//    }
//
//}
