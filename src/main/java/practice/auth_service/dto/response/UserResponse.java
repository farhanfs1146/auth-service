package practice.auth_service.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    // User id
    private Long id;

    // Employee reference id
    private String employeeId;

    // Username
    private String username;

    // Email address
    private String email;

    // First name
    private String firstName;

    // Last name
    private String lastName;

    // Contact number
    private String phoneNumber;

    // Active status
    private Boolean isActive;

    // Assigned roles
    private Set<RoleResponse> roles;
}