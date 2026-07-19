package com.devjoint.librarymanagementsystem.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryManagementOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Library Management System API")

                        .description("REST API for managing Authors, Books, Members and Loans.")

                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Khadija Ahmadova")
                                .email("xedicee616@email.com"))

                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))

                .externalDocs(new ExternalDocumentation()
                        .description("Project Repository")
                        .url("https://github.com/xedice616/devjoint_week1_checkpoint6"));
    }
}