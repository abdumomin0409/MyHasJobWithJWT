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


{
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIrOTk4OTMwODE1MzUxIiwiaWF0IjoxNjgzOTA5MDAyLCJpc3MiOiJodHRwczovL29ubGluZS5wZHAudXoiLCJleHAiOjE2ODM5NjkwMDJ9.TX_ZYaPxj8jFpct8nZzZ1n25GY6r5oWaEYBZPDibrctbE9ir-1ujbUz95aUMKIprruguifxfzmLPKAqwP_zgvQ",
    "accessTokenExpiry": "2023-05-13T09:10:02.680+00:00",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrOTk4OTMwODE1MzUxIiwiaWF0IjoxNjgzOTA5MDAyLCJpc3MiOiJodHRwczovL29ubGluZS5wZHAudXoiLCJleHAiOjE2ODQ3NzMwMDJ9.JnkTJ4gmThUBc3-fezu3rtyvsJaOqLKZdEoNX6mXHl4",
    "refreshTokenExpiry": "2023-05-22T16:30:02.692+00:00"
  },
  "success": true
}

   */

}
