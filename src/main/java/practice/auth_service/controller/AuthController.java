package practice.auth_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import practice.auth_service.dto.ApiResponse;
import practice.auth_service.dto.request.LoginRequest;
import practice.auth_service.dto.request.RegisterRequest;
import practice.auth_service.dto.response.AuthResponse;
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
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}