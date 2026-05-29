package practice.auth_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<UserResponse> getCurrentUser(
            Authentication authentication
    ) {

        // What is Authentication?
        // Spring automatically injects current authenticated user.
        // This object comes from:
        // SecurityContextHolder // which my JWT filter populated earlier.

        // Get logged-in username
        String username =
                authentication.getName();

        // Get user details
        UserResponse response =
                userService.getCurrentUser(username);

        // WHAT DOES authentication.getName() RETURN?
        // Returns:
        // Currently logged-in username
        // Example: Farhan

        return ResponseEntity.ok(response);
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

}
