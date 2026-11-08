package com.tecsup.config;

import com.tecsup.jwt.JwtAuthFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    // 1. Encriptador de contraseñas con algoritmo BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Administrador de Autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // 3. Cadena de filtros de seguridad (SecurityFilterChain)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF (no se requiere para APIs REST sin estado)
                .csrf(csrf -> csrf.disable())

                // Manejo de excepciones de seguridad no autorizadas
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("{\"error\": \"Forbidden\", \"message\": \"Access Denied - No tienes permisos suficientes\"}");
                        })
                )

                // Sesiones sin estado (STATELESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Reglas de Autorización de Rutas
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (Registro, Login y Endpoint público)
                        .requestMatchers("/api/auth/**", "/api/test/all").permitAll()

                        // Rutas de Administrador (Rol ADMIN)
                        .requestMatchers("/api/test/admin").hasRole("ADMIN")

                        // Rutas de Moderador (Rol MODERATOR)
                        .requestMatchers("/api/test/mod").hasRole("MODERATOR")

                        // Rutas de Usuario (Roles USER o ADMIN)
                        .requestMatchers("/api/test/user").hasAnyRole("USER", "ADMIN")

                        // Consola H2 para desarrollo
                        .requestMatchers("/h2-console/**").permitAll()

                        // Todas las demás solicitudes requieren autenticación previa
                        .anyRequest().authenticated()
                );

        // Permitir frames para consola H2
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        // Agregar filtro de validación JWT antes del filtro de usuario/password
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
