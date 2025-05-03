package vn.graybee.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

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
