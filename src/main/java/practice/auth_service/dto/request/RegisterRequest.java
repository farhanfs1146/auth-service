package practice.auth_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterRequest {

    // Employee reference id
    @NotNull(message = "Employee ID is required")
    @Schema(description = "Employee reference ID", example = "12345")
    private Long employeeId;

    // Login username
    @NotBlank(message = "Username is required")
    @Schema(description = "Login username", example = "john_doe")
    private String username;

    // User email
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Schema(description = "User email", example = "john.doe@example.com")
    private String email;

    // Raw password before encryption
    @NotBlank(message = "Password is required")
    @Schema(description = "Raw password before encryption", example = "SecurePassword123")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}