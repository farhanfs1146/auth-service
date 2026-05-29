package practice.auth_service.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import practice.auth_service.dto.request.UpdatedPasswordRequest;
import practice.auth_service.dto.response.UserResponse;

import practice.auth_service.entity.User;

import practice.auth_service.exception.ValidationException;

import practice.auth_service.repository.UserRepository;

import practice.auth_service.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // Inject PassEncoder to encode password before updating.
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getCurrentUser(String username) {

        User user = userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new ValidationException("User not found")
                        );

        return UserResponse.builder()
                .id(user.getId())
                .employeeId(user.getEmployeeId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }

    @Override
    public UserResponse changePassword(String username, UpdatedPasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ValidationException("User not found")
                );

        // Here we should hash the new password before saving
        // For simplicity, we are directly setting the new password
        // In production, always hash passwords using a strong algorithm like BCrypt
        //user.setPasswordHash(request.getNewPassword());

        // the above line will hash the new password before saving it to the database.
        // but there is no validation of previous/current password, you can add that as well if needed.
        // like validate username & current password to validate then update new password.

        // **************************
        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getOldPassword(),
                        user.getPasswordHash()
                );

        if (!passwordMatches) {
            throw new ValidationException("Invalid Old Password");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .employeeId(user.getEmployeeId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }
}