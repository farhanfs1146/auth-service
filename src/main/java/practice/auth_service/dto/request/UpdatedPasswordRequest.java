package practice.auth_service.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdatedPasswordRequest {

    // User existing password for login.
    @NotBlank(message = "Old password is required")
    @Schema(description = "User's current password for authentication", example = "currentPassword123", maxLength = 50)
    private String oldPassword;

    // User new password for login next time better security.
    @NotBlank(message = "New password is required")
    @Schema(description = "User's new password for authentication", example = "newPassword123",  maxLength = 50)
    private String newPassword;
}
