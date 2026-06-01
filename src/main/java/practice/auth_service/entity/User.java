package practice.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Employee reference id
    @Column(name = "employee_id", nullable = false, unique = true)
    private Long employeeId;

    // Username used for login
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // Email address
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Encrypted password
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    // User active status
    @Column(name = "is_active")
    private Boolean isActive;

    // Account lock status
    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    // Account expiration status
    @Column(name = "is_account_non_expired")
    private Boolean isAccountNonExpired;

    // Credential expiration status
    @Column(name = "is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    // Last successful login
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    // Creation timestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    // Last update timestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

}
