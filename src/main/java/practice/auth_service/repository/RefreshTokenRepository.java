package practice.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.auth_service.entity.RefreshToken;
import practice.auth_service.entity.User;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // Find refresh token
    Optional<RefreshToken> findByToken(String token);

    // Find tokens by user
    Optional<RefreshToken> findByUserId(Long userId);

    List<RefreshToken> findByUser(User user);

    // because later we'll implement:
    //Logout From Current Device
    //Logout From All Devices
    //which are very common interview and production requirements.
    void deleteByUser(User user);
}
