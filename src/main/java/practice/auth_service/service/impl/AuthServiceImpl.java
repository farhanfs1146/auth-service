package practice.auth_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.auth_service.dto.request.LoginRequest;
import practice.auth_service.dto.request.LogoutRequest;
import practice.auth_service.dto.request.RefreshTokenRequest;
import practice.auth_service.dto.request.RegisterRequest;
import practice.auth_service.dto.response.AuthResponse;
import practice.auth_service.dto.response.LogoutResponse;
import practice.auth_service.dto.response.RefreshTokenResponse;
import practice.auth_service.dto.response.UserResponse;
import practice.auth_service.entity.User;
import practice.auth_service.exception.ValidationException;
import practice.auth_service.repository.UserRepository;
import practice.auth_service.security.jwt.JwtService;
import practice.auth_service.service.AuthService;
import practice.auth_service.service.RefreshTokenService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // Spring automatically injects & No manual constructor needed.
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    // INJECT PASSWORD ENCODER
    private final PasswordEncoder passwordEncoder;

    // JWT Service Injection
    private final JwtService jwtService;

    // for refresh tokens/JWTs store in database.
    private final RefreshTokenService refreshTokenService;


    @Override
    public UserResponse register(RegisterRequest request) {

        // check whether user already exists with same employeeId
        if (userRepository.findByEmployeeId(request.getEmployeeId()).isPresent()) {
            throw new ValidationException("User with employeeId " + request.getEmployeeId() + " already exists");
        }

        // Check username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ValidationException("Username already exists");
        }

        // Check email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        // Create new user entity
        User user = User.builder()
                .employeeId(request.getEmployeeId())
                .username(request.getUsername())
                .email(request.getEmail())

                // TEMPORARY RAW PASSWORD
                // later we will encrypt using Bcrypt
                //.passwordHash(request.getPassword())

                // HASH PASSWORD DURING REGISTRATION
                // It is not Encryption, it is one way hashing which is un-reverable.
                // encode() method get raw password & Converts raw password into secure BCrypt hash.
                // example: password123(raw password) Convert assume as $2a$10$8fA.... which is (BCrypt hash)
                .passwordHash(passwordEncoder.encode(request.getPassword()))

                .isActive(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)

                .createdAt(LocalDateTime.now())
                .createdBy("system")
                .updatedAt(LocalDateTime.now())
                .updatedBy("system")

                .build();

        // Save user
        User savedUser = userRepository.save(user);
        // return DTO response
        return mapToResponse(savedUser);
    }

    // VERIFY PASSWORD DURING LOGIN
    @Override
    public AuthResponse login(LoginRequest request) {

        // Find user by username
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ValidationException("Invalid username or password")
                );

        // Verify password
        // matches(user_Typed_Raw_Password_While_Login, already_stored_Hashed_Password_Of_User))
        // it hashes this raw password and compared with stored hash.
        // if both matches then it returns true otherwise false.
        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPasswordHash()
                );

        if (!passwordMatches) {
            throw new ValidationException("Invalid username or password");
        }

        // Temporary response
        // Now we are creating JWT and assigning to access token.
        // Current flow will be:
        // Login Request
        //   ↓
        // Find user
        //   ↓
        // Verify password
        //   ↓
        // Generate JWT token
        //   ↓
        // Return token to client

        String accessToken = jwtService.generateToken(user.getUsername());

        // now adding the refresh token/JWT feature as well.
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        // as we moved to enterprise level, we should store refresh tokens in database to manage them like revoke, expire, etc.
        refreshTokenService.createRefreshToken(user, refreshToken);

        // Now login response will return real refresh tokens.
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresAt(null) // very soon we will use the exact expiration time of current JWT.
                .tokenType("Bearer")
                .build();
    }

    // *********************** Problem Without Refresh Token *********************
    // Suppose: Access token is valid for 15 minutes, after that user need to log-in again to get new access token.
    // This is not good user experience, so we use refresh token which is valid for longer time like 7 days, so user can get new access token without login again until refresh token expires.
    // Example: ACCESS_TOKEN_EXPIRATION = 1 hour

    // Flow: (Without Refresh Token)
    // User Login
    //    ↓
    // Access Token Generated
    //    ↓
    // User Uses APIs
    //    ↓
    // 1 Hour Passed
    //    ↓
    // Token Expired
    //    ↓
    // 401 Unauthorized
    //    ↓
    // User Must Log in Again

    // Bad user experience.
    //
    // Imagine:
    // HRMS Dashboard open
    // User working 4 hours
    // Token expires every hour
    // User would repeatedly log in.
    // Not practical.

    // Refresh Token Solution
    //
    // Instead:
    // User Login
    //    ↓
    // Access Token + Refresh Token Generated
    //    ↓
    // User Uses APIs
    //    ↓
    // 1 Hour Passed
    //    ↓
    // Access Token Expired
    //    ↓
    // User Calls Refresh Endpoint with Refresh Token
    //    ↓
    // If Refresh Token Valid, New Access Token Issued
    //    ↓
    // User Continues Using APIs without Re-Logging in

    // Simply means,
    // Login
    // ↓
    // Access Token (15 min)
    // Refresh Token (7 days)

    // Server:
    // Validate Refresh Token
    //      ↓
    // Generate New Access Token
    //      ↓
    // Return New Access Token

    // Response:
    //
    //{
    //   "accessToken":"new_token"
    //}

    // User never notices.
    // No login screen.

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        // Validate refresh token
        if (!jwtService.isRefreshTokenValid(refreshToken)) {

            throw new ValidationException("Invalid refresh token");
        }

        refreshTokenService.validateRefreshToken(refreshToken);

        // Extract username
        String username = jwtService.extractUsername(refreshToken);

        // Verify user still exists
        User user =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new ValidationException(
                                        "User not found"
                                )
                        );

        // Generate new access token
        String newAccessToken =
                jwtService.generateToken(
                        user.getUsername()
                );

        return RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) {

        refreshTokenService.revokeRefreshToken(
                request.getRefreshToken()
        );

        return LogoutResponse.builder()
                .username(jwtService.extractUsername(request.getRefreshToken()))
                .build();

    }



    // helping methods.
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .employeeId(user.getEmployeeId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }
}