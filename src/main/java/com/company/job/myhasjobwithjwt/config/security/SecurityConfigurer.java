package com.company.job.myhasjobwithjwt.config.security;

import com.company.job.myhasjobwithjwt.payload.AppErrorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Random;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurer {
    private final JWTAuthenticationFilter filter;
    private final ObjectMapper objectMapper;
    private final MyUserDetailsService userDetailsService;

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers((AUTH_URL + "/**"),
                        "/swagger-ui.html",
                        "/swagger-ui*/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/swagger-ui*/*swagger-initializer.js",
                        "/v3/api-docs*/**"
                )
                .permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            accessDeniedException.printStackTrace();
            String errorPath = request.getRequestURI();
            String errorMessage = accessDeniedException.getMessage();
            int errorCode = 403;
            AppErrorDTO appErrorDto = new AppErrorDTO(errorMessage, errorPath, errorCode);
            response.setStatus(errorCode);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, appErrorDto);
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            authException.printStackTrace();
            String errorPath = request.getRequestURI();
            String errorMessage = authException.getMessage();
            int errorCode = 401;
            AppErrorDTO appErrorDto = new AppErrorDTO(errorMessage, errorPath, errorCode);
            response.setStatus(errorCode);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, appErrorDto);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:8080",
                "https://hasjob.up.railway.app",
                "*"
        ));
//        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "DELETE", "PUT"
        ));
        configuration.setAllowedHeaders(List.of("*",
                "Accept",
                "Content-Type",
                "Authorization"
        ));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

}
