package vn.graybee.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "origin")
public class OriginProperties {

    private String techstore;

    private String techstoreDashboard;

    public String getTechstore() {
        return techstore;
    }

    public void setTechstore(String techstore) {
        this.techstore = techstore;
    }

    public String getTechstoreDashboard() {
        return techstoreDashboard;
    }

    public void setTechstoreDashboard(String techstoreDashboard) {
        this.techstoreDashboard = techstoreDashboard;
    }

}
