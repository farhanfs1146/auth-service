package practice.auth_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.auth_service.dto.request.LoginRequest;
import practice.auth_service.dto.request.RegisterRequest;
import practice.auth_service.dto.response.AuthResponse;
import practice.auth_service.dto.response.UserResponse;
import practice.auth_service.entity.User;
import practice.auth_service.repository.UserRepository;
import practice.auth_service.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public UserResponse register(RegisterRequest request) {

        // Check username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user entity
        User user = User.builder()
                .employeeId(request.getEmployeeId())
                .username(request.getUsername())
                .email(request.getEmail())

                // TEMPORARY RAW PASSWORD
                // later we will encrypt using Bcrypt
                .passwordHash(request.getPassword())

//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .phoneNumber(request.getPhoneNumber())

                .isActive(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)

                .build();

        // Save user
        User savedUser = userRepository.save(user);

        // Return response DTO
        return UserResponse.builder()
                .id(savedUser.getId())
                .employeeId(savedUser.getEmployeeId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
//                .firstName(savedUser.getFirstName())
//                .lastName(savedUser.getLastName())
//                .phoneNumber(savedUser.getPhoneNumber())
                .isActive(savedUser.getIsActive())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        // TEMPORARY LOGIN LOGIC
        // JWT will come later

        return AuthResponse.builder()
                .accessToken("TEMP_ACCESS_TOKEN")
                .refreshToken("TEMP_REFRESH_TOKEN")
                .tokenType("Bearer")
                .build();
    }
}