package practice.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureTestController {

    @GetMapping("/api/v1/test/secure")
    public String secureApi() {
        return "You accessed Secured/Proctected/Authenticated API successfully with credentials";
    }

    @GetMapping("/api/v1/test/public")
    public String publicApi() {
        return "Public API you get successfully without credentials";
    }
}