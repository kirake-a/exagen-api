package com.lisoft.exagen.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import static com.lisoft.exagen.domain.utils.Constants.OPEN_API_VERSION;

@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Exagen")
                        .version(OPEN_API_VERSION)
                        .description("API to manage all exagen requests")
                        .license(new License()
                                .name("Github Repository")
                                .url("https://github.com/kirake-a/exagen-api")
                        )
                );
    }
}

