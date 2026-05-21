package practice.auth_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.auth_service.dto.request.LoginRequest;
import practice.auth_service.dto.request.RegisterRequest;
import practice.auth_service.dto.response.AuthResponse;
import practice.auth_service.dto.response.UserResponse;
import practice.auth_service.entity.User;
import practice.auth_service.exception.ValidationException;
import practice.auth_service.repository.UserRepository;
import practice.auth_service.security.jwt.JwtService;
import practice.auth_service.service.AuthService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // Spring automatically injects & No manual constructor needed.
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    // INJECT PASSWORD ENCODER
    private final PasswordEncoder passwordEncoder;

    // JWT Service Injection
    private final JwtService jwtService;

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

        String accessToken =
                jwtService.generateToken(user.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
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