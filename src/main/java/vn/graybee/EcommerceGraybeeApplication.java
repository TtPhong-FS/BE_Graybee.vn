package vn.graybee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ReactiveElasticsearchClientAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = {
        ReactiveElasticsearchClientAutoConfiguration.class,
})
public class EcommerceGraybeeApplication {

    static Logger logger = LoggerFactory.getLogger(EcommerceGraybeeApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(EcommerceGraybeeApplication.class, args);

        logger.info("version:0.0.2");
    }


}
