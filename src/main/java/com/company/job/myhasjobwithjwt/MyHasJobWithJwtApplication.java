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
        return () -> Optional.ofNullable(sessionUser.id());
    }

    /*
    930815352

  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIrOTk4OTMwODE1MzUxIiwiaWF0IjoxNjg0MDY3MDkyLCJpc3MiOiJodHRwczovL29ubGluZS5wZHAudXoiLCJleHAiOjE2ODQxMjcwOTJ9.lbEcT0keAQ_lusDyTm8boiDf8xZxVNQiKK-DRkqTcTJIDeuWHFElFpbXxChqQsoO9fNywMoMPM7bXiBRifGctQ",
    "accessTokenExpiry": "2023-05-15T05:04:52.034+00:00",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrOTk4OTMwODE1MzUxIiwiaWF0IjoxNjg0MDY3MDkyLCJpc3MiOiJodHRwczovL29ubGluZS5wZHAudXoiLCJleHAiOjE2ODQ5MzEwOTJ9.zL1iBT9-AYcvIwj0u-uOiXG1eGUzIIMWsYeO1J4xzHk",
    "refreshTokenExpiry": "2023-05-24T12:24:52.047+00:00"

   */

}
