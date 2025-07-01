package vn.graybee.common.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import vn.graybee.checkers.StartupConnectionChecker;

@EnableCaching
@Configuration
public class DBConfig {

    static final Logger logger = LoggerFactory.getLogger(DBConfig.class);

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connect) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connect);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        String host = System.getenv("ELS_HOST");
        String username = System.getenv("ELS_USERNAME");
        String password = System.getenv("ELS_PASSWORD");

        if (host == null) {
            host = "http://localhost:9200"; // fallback cho local
        }

        if (username == null || username.isBlank()) {
            username = "";
        }

        if (password == null || password.isBlank()) {
            password = "";
        }
        // Setup Basic Auth
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(username, password)
        );

        RestClientBuilder builder = RestClient.builder(HttpHost.create(host))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                );

        // Optional: Test connection
        try (RestClient testClient = builder.build()) {
            Response response = testClient.performRequest(new Request("GET", "/"));
            logger.info("Elasticsearch Status: {}", response.getStatusLine().getStatusCode());
        } catch (Exception e) {
            logger.warn("Failed to connect to Elasticsearch: {}", e.getMessage());
        }

        RestClient restClient = builder.build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient,
                new JacksonJsonpMapper()
        );

        return new ElasticsearchClient(transport);
    }


}
