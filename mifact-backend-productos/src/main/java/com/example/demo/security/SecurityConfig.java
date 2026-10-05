package com.example.demo.security;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final UsuarioRepository usuarioRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors().and()
                .csrf().disable() // La API utiliza JWT, por lo que no necesitamos CSRF.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No almacenamos sesiones en el servidor.
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/api/auth/login",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Creamos un usuario por defecto al arrancar si la BD está vacía
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                // Encriptamos la clave "admin123" usando BCrypt
                admin.setPassword(passwordEncoder().encode("admin2026"));
                usuarioRepository.save(admin);
                System.out.println("====== USUARIO ADMIN CREADO (User: admin | Pass: admin2026) ======");
            }
        };
    }
}