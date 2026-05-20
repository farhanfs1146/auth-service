package practice.auth_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import practice.auth_service.entity.UserRole;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    // Get all roles assigned to a user
    List<UserRole> findByUserId(Long userId);

    // Get all users assigned to a role
    List<UserRole> findByRoleId(Long roleId);
}