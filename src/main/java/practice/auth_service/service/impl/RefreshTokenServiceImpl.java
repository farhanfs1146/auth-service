package practice.auth_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.auth_service.entity.RefreshToken;
import practice.auth_service.entity.User;
import practice.auth_service.exception.ValidationException;
import practice.auth_service.repository.RefreshTokenRepository;
import practice.auth_service.service.RefreshTokenService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(
            User user,
            String token
    ) {

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .user(user)
                        .token(token)
                        .revoked(false)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusDays(7)) // Example: 7 days validity
                        .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken validateRefreshToken(
            String token
    ) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new ValidationException(
                                        "Refresh token not found"
                                )
                        );

        if (Boolean.TRUE.equals(refreshToken.getRevoked())) {

            throw new ValidationException(
                    "Refresh token revoked"
            );
        }

        return refreshToken;
    }

    @Override
    public void revokeRefreshToken(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token).orElseThrow(() ->
                        new ValidationException(
                                "Refresh token not found"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
}