package practice.auth_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import practice.auth_service.dto.ApiResponse;
import practice.auth_service.dto.request.UpdatedPasswordRequest;
import practice.auth_service.dto.response.UserResponse;
import practice.auth_service.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // FLOW VISUALIZATION:
    // 1. Client sends request with JWT token
    // 2. JWT filter validates token and sets Authentication object in SecurityContextHolder
    // 3. This endpoint is called, and Spring injects the Authentication object
    // 4. We extract the username from the Authentication object
    // 5. We fetch user details based on the username

    // or say:
    // JWT Token
    //   ↓
    //JwtAuthenticationFilter
    //   ↓
    //Authentication Object
    //   ↓
    //SecurityContextHolder
    //   ↓
    //Controller receives Authentication

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(
            Authentication authentication
    ) {

        // What is Authentication?
        // Spring automatically injects current authenticated user.
        // This object comes from:
        // SecurityContextHolder // which my JWT filter populated earlier.

        // Get logged-in username
        String username = authentication.getName();
        // Get user details
        UserResponse response = userService.getCurrentUser(username);

        // WHAT DOES authentication.getName() RETURN?
        // Returns:
        // Currently logged-in username
        // Example: Farhan

        return ResponseEntity.ok(ApiResponse.success("Current logged-in user details fetched successfully", response));

                // Flow of this end-point
                // ************************
                // What /me Actually Teaches
                // When the frontend sends:

                // GET /api/v1/users/me
                //Authorization: Bearer eyJobGciOi...

                // Request
                //   │
                //   ▼
                //JwtAuthenticationFilter
                //   │
                //   ▼
                //Validate JWT
                //   │
                //   ▼
                //Extract username
                //   │
                //   ▼
                //Create Authentication object
                //   │
                //   ▼
                //SecurityContextHolder
                //   │
                //   ▼
                //Controller receives Authentication
                //   │
                //   ▼
                //authentication.getName()
                //   │
                //   ▼
                // Farhan
    }

    // WHAT YOU JUST LEARNED
    // You now understand:

    // | Concept                | Meaning                       |
    // | ---------------------- | ----------------------------- |
    // | Authentication object  | current logged-in user        |
    // | SecurityContextHolder  | stores auth user              |
    // | JWT → User flow        | token identifies user         |
    // | Controller injection   | Spring injects Authentication |
    // | Current user retrieval | industry-standard approach    |

    // VERY IMPORTANT PROFESSIONAL CONCEPT
    // This pattern:
    // Authentication authentication object: is used everywhere in enterprise applications:
    // audit logs
    // createdBy
    // updatedBy
    // user profile
    // permissions
    // activity tracking
    // notifications etc.


    // change password end-point
    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse<UserResponse>> changePassword(@Valid @RequestBody UpdatedPasswordRequest request, Authentication authentication) {
        String username = authentication.getName();
        UserResponse response = userService.changePassword(username, request);
        return ResponseEntity.ok(ApiResponse.success("New password has been updated successfully", response));
    }

}
