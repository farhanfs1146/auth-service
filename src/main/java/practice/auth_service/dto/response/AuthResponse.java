package practice.auth_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    // JWT access token
    private String accessToken;

    // Refresh token
    private String refreshToken;

    // Token type
    private String tokenType;

    // Access token expiration
    private LocalDateTime expiresAt;

    // Logged in user details
    private UserResponse user;
}