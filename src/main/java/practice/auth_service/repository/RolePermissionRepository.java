package practice.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.auth_service.entity.RolePermission;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    // Get permissions assigned to role
    List<RolePermission> findByRoleId(Long roleId);

    // Get roles using permission
    List<RolePermission> findByPermissionId(Long permissionId);
}
