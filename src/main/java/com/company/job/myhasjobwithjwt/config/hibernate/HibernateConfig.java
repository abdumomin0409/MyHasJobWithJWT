package com.company.job.myhasjobwithjwt.config.hibernate;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.jetbrains.annotations.NotNull;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.*;

@Configuration
public class HibernateConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }


    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(" Has job")
                        .description("API for applicants finding job")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Abdumo'min Shamshidinov ")
                                .email("shamshiddinovabdumomin2003@gmail.com")
                                .url("https://github.com/abdumomin0409"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .termsOfService("http://swagger.io/terms/"))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Wikipedia Documentation")
                        .url("https://springshop.wiki.github.org/docs"))
                .servers(List.of(
                        new Server().url("https://hasjob.up.railway.app").description("Development Server"),
                        new Server().url("http://localhost:8080").description("My test Server"))
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components((new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                ));
    }

    @Bean
    public GroupedOpenApi allOpenApi() {
        return GroupedOpenApi.builder()
                .group("All")
                .pathsToMatch(BASE_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authOpenApi() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .pathsToMatch(AUTH_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi chatOpenApi() {
        return GroupedOpenApi.builder()
                .group("Chat")
                .pathsToMatch(CHAT_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi profileOpenApi() {
        return GroupedOpenApi.builder()
                .group("Profile")
                .pathsToMatch(PROFILE_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi cwOpenApi() {
        return GroupedOpenApi.builder()
                .group("Ads")
                .pathsToMatch(ADS_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi jobOpenApi() {
        return GroupedOpenApi.builder()
                .group("Job type")
                .pathsToMatch(JOB_TYPE_URL + "/**")
                .build();
    }


}
