package practice.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_roles", schema = "auth_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // User reference
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Role reference
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // Role assignment timestamp
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    // Admin/system who assigned role
    @Column(name = "assigned_by")
    private String assignedBy;
}
