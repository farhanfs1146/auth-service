package practice.auth_service.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    // CURRENT PROBLEM
    //
    // Right now Spring Security may return:
    //
    // empty body
    // HTML response
    // ugly default error page
    // unclear message
    //
    // This is not suitable for:
    //
    // frontend apps
    // mobile apps
    // microservices

    // WHAT WE WILL BUILD
    // Custom AuthenticationEntryPoint
    //
    // This handles:
    //
    // Unauthenticated requests
    //
    // Examples:
    //
    // no JWT
    // expired JWT
    // invalid JWT
    // WHAT IS ENTRY POINT?
    //
    // Think of it like:
    //
    // Security failure gateway
    //
    // Whenever authentication fails:
    //
    // Spring Security
    //    ↓
    // calls AuthenticationEntryPoint
    //    ↓
    // custom response returned

    // VERY IMPORTANT LEARNING
    // WHAT IS commence()?
    //
    // Spring automatically calls this method when:
    //
    // authentication fails
    //
    // Examples:
    //
    // invalid token
    // no token
    // expired token

    // WHAT IS ObjectMapper?
    //
    //Provided by:
    //
    //Jackson databind
    //
    //Used for:
    //
    //Java Object → JSON
    //JSON → Java Object
    //
    //Spring Boot internally uses Jackson everywhere.

    // WHAT IS response.getOutputStream()?
    //Writes data directly into HTTP response body.

    // to apply or use it, we need to configure it in our SecurityConfig class as the entry point for unauthorized access.
    // Register Entry Point In SecurityConfig

    // COMPLETE FLOW NOW

    // Request
    //   ↓
    //JWT invalid/missing
    //   ↓
    //Authentication fails
    //   ↓
    //JwtAuthenticationEntryPoint
    //   ↓
    //Custom JSON response

    // WHAT YOU LEARNED
    // | Concept                     | Meaning                  |
    // | --------------------------- | ------------------------ |
    // | AuthenticationEntryPoint    | handles auth failures    |
    // | Custom security responses   | professional APIs        |
    // | ObjectMapper                | Java ↔ JSON              |
    // | Security exception pipeline | enterprise security flow |
    // | Response customization      | API standardization      |


    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        // Set response type
        response.setContentType("application/json");

        // Set status code
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Create JSON response body
        Map<String, Object> errorResponse =
                new HashMap<>();

        errorResponse.put("timestamp", LocalDateTime.now().toString());

        errorResponse.put("status", 401);

        errorResponse.put("error", "Unauthorized");

        errorResponse.put("message", authException.getMessage());

        errorResponse.put("path", request.getRequestURI());

        // Convert Java object → JSON
        ObjectMapper mapper =
                new ObjectMapper();

        mapper.writeValue(
                response.getOutputStream(),
                errorResponse
        );
    }
}