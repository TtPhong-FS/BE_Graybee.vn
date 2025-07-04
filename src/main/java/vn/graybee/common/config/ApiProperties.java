package vn.graybee.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    public Admin admin;

    public String home;

    public String products;

    public String collections;

    public String carts;

    public String account;

    public String auth;

    public String orders;


    @Setter
    @Getter
    public static class Admin {

        private String basePath;

        private String categories;

        private String attributes;

        private String products;

        private String orders;

        private String inventories;

        private String permissions;

        private String users;

        private String customers;

        private String imageKit;

        private String deliveries;

        private String payments;

    }

}
