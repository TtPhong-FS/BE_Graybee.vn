package vn.graybee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ReactiveElasticsearchClientAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = {
        ReactiveElasticsearchClientAutoConfiguration.class,
})
public class EcommerceGraybeeApplication {

    public static void main(String[] args) {

        SpringApplication.run(EcommerceGraybeeApplication.class, args);

    }


}
