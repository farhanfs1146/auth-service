package practice.auth_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.auth_service.dto.request.LoginRequest;
import practice.auth_service.dto.request.RegisterRequest;
import practice.auth_service.dto.response.AuthResponse;
import practice.auth_service.dto.response.UserResponse;
import practice.auth_service.entity.User;
import practice.auth_service.exception.ValidationException;
import practice.auth_service.repository.UserRepository;
import practice.auth_service.service.AuthService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

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
                .passwordHash(request.getPassword())

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

    @Override
    public AuthResponse login(LoginRequest request) {

        // check whether the login user is exits with same username & password?
        if (!userRepository.existsByUsernameAndPasswordHash(request.getUsername(), request.getPassword())) {
            throw new ValidationException("Invalid username or password");
        }
        
        // TEMPORARY LOGIN LOGIC
        // JWT will come later

        return AuthResponse.builder()
                .accessToken("TEMP_ACCESS_TOKEN")
                .refreshToken("TEMP_REFRESH_TOKEN")
                .tokenType("Bearer")
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