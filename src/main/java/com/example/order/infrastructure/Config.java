package com.example.order.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Ensina o Spring a injetar o BCrypt lá no seu CreateCustomerService
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Desativa a tela de login padrão do Spring Security e permite o acesso a todas as rotas (incluindo Swagger)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita o CSRF (comum para APIs REST)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite todas as requisições (ajuste no futuro se quiser bloquear algo)
            );
        return http.build();
    }
}