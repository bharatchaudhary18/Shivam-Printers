package com.shivamprinters.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI shivamPrintersOpenAPI(AppProperties appProperties) {
        return new OpenAPI()
                .info(new Info()
                        .title(appProperties.getName() + " API")
                        .description("REST API for " + appProperties.getName())
                        .version("1.0.0")
                        .contact(new Contact()
                                .name(appProperties.getName())
                                .email(appProperties.getAdmin().getEmail())))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new Components()
                        .addSecuritySchemes("basicAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")));
    }
}
