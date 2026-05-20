package practice.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.auth_service.entity.Permission;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    // Find permission by name
    Optional<Permission> findByPermissionName(String permissionName);

    // Check permission exists
    boolean existsByPermissionName(String permissionName);
}
