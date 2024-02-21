package io.goorm.ainfras.target.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("GoormShop Target Server")
                .description("AinfraS Log 수집용 Target 서버 내부 API")
                .version("v0.0.1");

        Server develop = new Server()
                .url("http://localhost:5050")
                .description("Develop server");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Authorization");

        return new OpenAPI()
                .addServersItem(develop)
                .info(info)
                .components(new Components().addSecuritySchemes("Authorization", securityScheme))
                .security(Collections.singletonList(securityRequirement));
    }
}