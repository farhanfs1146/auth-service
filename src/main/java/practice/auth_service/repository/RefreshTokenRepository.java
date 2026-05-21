package practice.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.auth_service.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // Find refresh token
    Optional<RefreshToken> findByToken(String token);

    // Find tokens by user
    Optional<RefreshToken> findByUserId(Long userId);
}
