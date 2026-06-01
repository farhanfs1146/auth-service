package practice.auth_service.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import practice.auth_service.security.handler.JwtAuthenticationEntryPoint;
import practice.auth_service.security.jwt.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // Inject JWT filter
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Inject AuthenticationEntryPoint
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for APIs (not needed for stateless JWT auth)
                .csrf(csrf -> csrf.disable())
                // Stateless session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // add here jwt Authentication Entry Point/
                // WHAT YOU LEARNED
                // | Concept                     | Meaning                  |
                // | --------------------------- | ------------------------ |
                // | AuthenticationEntryPoint    | handles auth failures    |
                // | Custom security responses   | professional APIs        |

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                // Configure endpoint permissions
                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login",
                                // we also need to add swagger apis to access
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/swagger-ui/index.html",
                                "/api/v1/auth/refresh-token",
                                "/api/v1/auth/logout"
                        ).permitAll()

                        // Everything else protected, means you have to pass Valid JWT token to access other endpoints.
                        .anyRequest().authenticated()
                )

                // Add JWT filter before Spring auth filter.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Optional
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}