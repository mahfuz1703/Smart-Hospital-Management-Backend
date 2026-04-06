package com.smarthealth.security.debug;

import java.util.Map;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeanDebugConfig {

    @Bean
    ApplicationRunner listSecurityFilterChains(ApplicationContext ctx) {
        return args -> {
            Map<String, SecurityFilterChain> chains = ctx.getBeansOfType(SecurityFilterChain.class);
            System.out.println("==== SecurityFilterChain beans (" + chains.size() + ") ====");
            chains.forEach((name, bean) -> System.out.println(" - " + name + " -> " + bean.getClass().getName()));
            System.out.println("==========================================");
        };
    }
}