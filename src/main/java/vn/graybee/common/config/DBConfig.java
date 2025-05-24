package vn.graybee.common.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class DBConfig {

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
        String elasticHost = System.getenv("ELS_HOST");
        if (elasticHost == null) {
            elasticHost = "localhost";
        }
        RestClient restClient = RestClient.builder(
                new HttpHost(elasticHost, 9200)
        ).build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper()
        );

        return new ElasticsearchClient(transport);
    }

//     .setDefaultHeaders(
//                new Header[]{
//        new BasicHeader(HttpHeaders.AUTHORIZATION,
//                "Basic " + Base64.getEncoder().encodeToString("elastic:admin123".getBytes()))
//    }
//        )
}
