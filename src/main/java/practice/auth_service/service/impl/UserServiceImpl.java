package practice.auth_service.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import practice.auth_service.dto.response.UserResponse;

import practice.auth_service.entity.User;

import practice.auth_service.exception.ValidationException;

import practice.auth_service.repository.UserRepository;

import practice.auth_service.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository userRepository;

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
}