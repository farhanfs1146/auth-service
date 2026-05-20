package practice.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "permissions", schema = "auth_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Permission identifier
    @Column(name = "permission_name", nullable = false, unique = true)
    private String permissionName;

    // Permission description
    @Column(name = "description")
    private String description;

    // Creation timestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Role permission mappings
//    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
//    private Set<RolePermission> rolePermissions;
}