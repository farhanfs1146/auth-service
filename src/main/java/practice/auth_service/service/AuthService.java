package practice.auth_service.service;

import practice.auth_service.dto.request.LoginRequest;
import practice.auth_service.dto.request.RegisterRequest;
import practice.auth_service.dto.response.AuthResponse;
import practice.auth_service.dto.response.UserResponse;

public interface AuthService {

    // Register new user
    UserResponse register(RegisterRequest request);

    // User login
    AuthResponse login(LoginRequest request);
}