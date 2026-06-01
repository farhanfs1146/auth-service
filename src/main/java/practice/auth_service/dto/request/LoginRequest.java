package practice.auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginRequest {

    // Username for login
    @NotBlank(message = "Username is required")
    private String username;

    // Raw password entered by user
    @NotBlank(message = "Password is required")
    private String password;
}