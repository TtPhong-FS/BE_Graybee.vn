package vn.graybee.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String adminSecretKey;

    private Long adminExpiration;

    private String customerSecretKey;

    private Long customerExpiration;

    private String header;

    private String prefix;

    public String getAdminSecretKey() {
        return adminSecretKey;
    }

    public void setAdminSecretKey(String adminSecretKey) {
        this.adminSecretKey = adminSecretKey;
    }

    public Long getAdminExpiration() {
        return adminExpiration;
    }

    public void setAdminExpiration(Long adminExpiration) {
        this.adminExpiration = adminExpiration;
    }

    public String getCustomerSecretKey() {
        return customerSecretKey;
    }

    public void setCustomerSecretKey(String customerSecretKey) {
        this.customerSecretKey = customerSecretKey;
    }

    public Long getCustomerExpiration() {
        return customerExpiration;
    }

    public void setCustomerExpiration(Long customerExpiration) {
        this.customerExpiration = customerExpiration;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
