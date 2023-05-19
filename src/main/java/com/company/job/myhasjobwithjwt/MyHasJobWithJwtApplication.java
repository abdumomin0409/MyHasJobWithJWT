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

    public static void main(String[] args) {
        SpringApplication.run(MyHasJobWithJwtApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider(SessionUser sessionUser) {
        return () -> Optional.ofNullable(sessionUser.id());
    }

    /*
    930815352

  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIrOTk4OTMwODE1MzUxIiwiaWF0IjoxNjg0MTQ0MzkwLCJleHAiOjE2ODQyMDQzOTB9.fhlw9r3ld3Wo66wr0QbTIwkZnRRM_PDk6-JKCq_m2oKjKmVcMzq8d3K7QVsVyZ3gpMEGeyiNwISAW4DjTr55yQ",
    "accessTokenExpiry": "2023-05-16T02:33:10.220+00:00",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrOTk4OTMwODE1MzUxIiwiaWF0IjoxNjg0MTQ0MzkwLCJleHAiOjE2ODUwMDgzOTB9.Ktsc1phXKefXuGDg22wmoVMYualtJFay6NF6-M317Q8",
    "refreshTokenExpiry": "2023-05-25T09:53:10.250+00:00"

   */

}
