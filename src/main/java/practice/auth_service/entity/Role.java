package practice.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "auth_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Role name like ADMIN
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    // Role description
    @Column(name = "description")
    private String description;

    // Creation timestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // User role mappings
//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
//    private Set<UserRole> userRoles;

    // Role permission mappings
//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
//    private Set<RolePermission> rolePermissions;
}