package com.company.job.myhasjobwithjwt;

import com.company.job.myhasjobwithjwt.config.security.SessionUser;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Optional;

@SpringBootApplication
@OpenAPIDefinition
@EnableWebSecurity
@EnableJpaAuditing
@EnableAsync
public class MyHasJobWithJwtApplication {

    private final SessionUser sessionUser;

    public MyHasJobWithJwtApplication(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyHasJobWithJwtApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(sessionUser.user().getId());
    }

    /*
    930815351

  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIrOTk4OTMwODE1MzUxIiwiaWF0IjoxNjgzNDI5NTE4LCJpc3MiOiJodHRwczovL29ubGluZS5wZHAudXoiLCJleHAiOjE2ODM1MTk1MTh9.iXKfaC5WeAtlVuRtUpTEwQLgpFS6KjbBi5oiYmX4imZnlkMGae_jY9V7rXqMhSr0PxCO8Un8VEKjnOjtr0cmLA",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrOTk4OTMwODE1MzUxIiwiaWF0IjoxNjgzNDI5NTE4LCJpc3MiOiJodHRwczovL29ubGluZS5wZHAudXoiLCJleHAiOjE2ODQzMjk1MTh9.BhfyPJh9PUlS0j9_t95tYY2Vwv5yGbGxm40HUyqivr4"


   */

}
