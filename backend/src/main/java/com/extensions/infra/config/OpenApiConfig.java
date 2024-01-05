package com.extensions.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto de inventário")
                        .version("v1")
                        .description("Projeto realizado para implementação de um inventário de ativos.")
                        .license(new License().name("Apache 2.0")
                                .url("https://url.com")));
    }

}
