package com.enesderin.portfolio.config;

import com.enesderin.portfolio.jwt.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String ADMIN = "/admin/**";
    private final String REGISTER = "/register";
    private final String AUTHENTICATE = "/authenticate";
    private final String REFRESH_TOKEN = "/refreshToken";
    private final String[] SWAGGER_PATHS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    AuthenticationFilter authenticationFilter;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(ADMIN).authenticated()
                            .requestMatchers(REGISTER, AUTHENTICATE, REFRESH_TOKEN).permitAll()
                            .requestMatchers(SWAGGER_PATHS).permitAll()
                            .anyRequest().permitAll();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
