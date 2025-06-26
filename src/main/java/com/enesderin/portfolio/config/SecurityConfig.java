package com.enesderin.portfolio.config;

import com.enesderin.portfolio.jwt.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] ADMIN_PATHS = {
            "/admin/**",
            "/project/admin/**",
            "/about/admin/**",
            "/skill/admin/**",
    };
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(ADMIN_PATHS).hasRole("ADMIN")
                            .requestMatchers(REGISTER, AUTHENTICATE, REFRESH_TOKEN).permitAll()
                            .requestMatchers(SWAGGER_PATHS).permitAll()
                            .anyRequest().permitAll();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://127.0.0.1:5500"); // frontend URL'si
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE vs.
        configuration.addAllowedHeader("*"); // Tüm headerlara izin ver
        configuration.setAllowCredentials(true); // Cookie veya header izinleri için

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
