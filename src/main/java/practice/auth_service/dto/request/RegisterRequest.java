package practice.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    // Employee reference id
    private String employeeId;

    // Login username
    @NotBlank(message = "Username is required")
    private String username;

    // User email
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    // Raw password before encryption
    @NotBlank(message = "Password is required")

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    // First name
    @NotBlank(message = "First name is required")
    private String firstName;

    // Last name
    @NotBlank(message = "Last name is required")
    private String lastName;

    // Contact number
    private String phoneNumber;
}