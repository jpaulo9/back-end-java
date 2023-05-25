package com.rest.api.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI cuOpenAPI(){
        return new  OpenAPI().
                info(new Info()
                        .title("RESTFUL Spring Boot")
                        .version("v1")
                        .description("I like of programminng")
                        .termsOfService("https://teste.com.br")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://teste.com.br")
                        )
                );
    }
}
