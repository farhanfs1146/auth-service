package practice.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens", schema = "auth_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Refresh token value
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    // Revoked status
    @Column(name = "revoked")
    private Boolean revoked;

    // Expiration datetime
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    // Creation datetime
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Token owner
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}