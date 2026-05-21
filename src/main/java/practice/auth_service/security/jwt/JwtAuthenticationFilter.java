package practice.auth_service.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Read Authorization header
        final String authHeader =
                request.getHeader("Authorization");

        // If header missing OR not Bearer token
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer " from token
        String jwtToken =
                authHeader.substring(7);

        // Extract username from token
        String username =
                jwtService.extractUsername(jwtToken);

        // If username exists AND user not authenticated yet
        if (username != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            // Load user from DB
            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(username);

            // Validate token
            if (jwtService.isTokenValid(
                    jwtToken,
                    userDetails.getUsername()
            )) {

                // Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // Store authenticated user in Spring Security
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        // Continue request
        filterChain.doFilter(request, response);
    }
}