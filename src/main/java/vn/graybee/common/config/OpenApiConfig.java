package vn.graybee.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


public class OpenApiConfig {

    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("API-server document for Techstore"));
    }

}
