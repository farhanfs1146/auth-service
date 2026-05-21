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
    private Long employeeId;

    // Username
    private String username;

    // Email address
    private String email;

    // Active status
    private Boolean isActive;

    // Assigned roles
    private Set<RoleResponse> roles;
}