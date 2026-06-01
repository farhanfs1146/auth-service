package practice.auth_service.service;

import practice.auth_service.dto.request.LoginRequest;
import practice.auth_service.dto.request.LogoutRequest;
import practice.auth_service.dto.request.RefreshTokenRequest;
import practice.auth_service.dto.request.RegisterRequest;
import practice.auth_service.dto.response.AuthResponse;
import practice.auth_service.dto.response.LogoutResponse;
import practice.auth_service.dto.response.RefreshTokenResponse;
import practice.auth_service.dto.response.UserResponse;

public interface AuthService {

    // Register new user
    UserResponse register(RegisterRequest request);

    // User login
    AuthResponse login(LoginRequest request);

    // refresh token
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    LogoutResponse logout(LogoutRequest request);
}