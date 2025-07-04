package vn.graybee.common.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private PublicApi publicApi;

    private AdminApi adminApi;

    private PrivateApi privateApi;


    @Getter
    public static class PublicApi {

        private String base;

        private String home;

        private String auth;

        private String carts;

        private String products;

    }

    @Getter
    public static class AdminApi {

        private String base;

        private String auth;

        private String products;

        private String categories;

        private String attributes;

        private String orders;

        private String inventories;

    }

    @Getter
    public static class PrivateApi {

        private String account;

        private String profile;

        private String address;

        private String favorites;

    }

}
