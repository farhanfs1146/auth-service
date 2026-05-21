package practice.auth_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import practice.auth_service.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Find role by role name
    Optional<Role> findByRoleName(String roleName);

    // Check role exists
    boolean existsByRoleName(String roleName);
}
