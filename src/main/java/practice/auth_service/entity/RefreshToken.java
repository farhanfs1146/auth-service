package practice.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
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

    /**
     * Actual JWT refresh token
     **/
    // Refresh token value
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    /**
     * Logout support
     **/
    // Revoked status
    // false = active
    // true = logged out
    @Column(name = "revoked")
    private Boolean revoked;

    /**
     * Expiry time
     **/
    // Expiration datetime
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    // Creation datetime
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    // updation column
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * Which user owns this token
     **/
    // Token owner.
    // Many refresh tokens can belong to one user.
    // Logs in from:
    // Laptop
    // Mobile
    // Tablet etc.
    // Database will store:
    // Tokens like
    // token1
    // token2
    // token3
    // All belong to same user.
    // Therefore: we use many to one
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}