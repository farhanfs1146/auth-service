package practice.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "role_permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Role reference
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // Permission reference
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    // Assignment timestamp
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    // Assigned by admin/system
    @Column(name = "assigned_by")
    private String assignedBy;
}
