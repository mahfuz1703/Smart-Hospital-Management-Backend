package com.smarthealth.security.config;

import com.smarthealth.security.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter,
            @Qualifier("mvcHandlerMappingIntrospector") HandlerMappingIntrospector introspector
    ) throws Exception {

        System.out.println(">>> LOADED SecurityConfig securityFilterChain()");

        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> res.sendError(401))
                        .accessDeniedHandler((req, res, e) -> res.sendError(403))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(mvc.pattern("/error")).permitAll()
                        .requestMatchers(mvc.pattern("/api/v1/auth/**")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/actuator/health")).permitAll()
                        .requestMatchers(mvc.pattern("/v3/api-docs/**")).permitAll()
                        .requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
                        .requestMatchers(mvc.pattern("/swagger-ui.html")).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    org.springframework.boot.ApplicationRunner printHash(org.springframework.security.crypto.password.PasswordEncoder encoder) {
        return args -> System.out.println("HASH=" + encoder.encode("Passw0rd!"));
    }
}