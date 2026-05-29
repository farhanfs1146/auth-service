package practice.auth_service.service;

import practice.auth_service.dto.request.UpdatedPasswordRequest;
import practice.auth_service.dto.response.UserResponse;

public interface UserService {

    UserResponse getCurrentUser(String username);

    UserResponse changePassword(String username, UpdatedPasswordRequest request);
}