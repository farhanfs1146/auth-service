package practice.auth_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import practice.auth_service.dto.ApiResponse;
import practice.auth_service.dto.request.LoginRequest;
import practice.auth_service.dto.request.LogoutRequest;
import practice.auth_service.dto.request.RefreshTokenRequest;
import practice.auth_service.dto.request.RegisterRequest;
import practice.auth_service.dto.response.AuthResponse;
import practice.auth_service.dto.response.LogoutResponse;
import practice.auth_service.dto.response.RefreshTokenResponse;
import practice.auth_service.dto.response.UserResponse;

import practice.auth_service.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Register API
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        // @RequestBody converts JSON → Java object.
        // @Valid Triggers validation annotations automatically.
        var response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("New User with username : " + response.getUsername() + " has been created successfully", response));
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        var response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("User logged in successfully", response));
    }

    // Purpose:
    // Receive refresh token
    // Validate refresh token
    // Issue new access token
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        System.out.println("REFRESH JWT/Token ENDPOINT HIT");
        var response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<LogoutResponse>> logout(@Valid @RequestBody LogoutRequest request) {

       var response = authService.logout(request);
        return ResponseEntity.ok(ApiResponse.success("User Logged-out successfully", response));
    }

}