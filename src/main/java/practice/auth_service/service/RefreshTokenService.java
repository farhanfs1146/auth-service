package practice.auth_service.service;

import practice.auth_service.entity.RefreshToken;
import practice.auth_service.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user, String token);

    RefreshToken validateRefreshToken(String token);

    void revokeRefreshToken(String token);
}