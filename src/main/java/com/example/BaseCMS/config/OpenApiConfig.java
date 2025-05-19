package com.example.BaseCMS.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${application.title}") String title,
                                 @Value("${application.description}") String description,
                                 @Value("${application.version}") String version,
                                 @Value("${application.license}") String license) {

        Server server = new Server();
        server.setUrl("https://api.antechgroup.com.vn");

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth", bearerScheme()))
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .license(new License().name(license)))
                        .servers(List.of(server)) // 👈 Cấu hình thủ công base URL
        ;
    }

    private SecurityScheme bearerScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
