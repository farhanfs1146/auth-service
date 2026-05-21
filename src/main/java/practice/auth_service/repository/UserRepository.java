package practice.auth_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import practice.auth_service.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Concept ->  Optional Object
    // why we use Optional? because it can handle null values gracefully and provides a more expressive way to indicate that a value may be absent.
    // It helps to avoid NullPointerExceptions and makes the code more readable by explicitly indicating that a value may not be present.

    // Concept -> Query Derivation
    // Spring generates SQL from method names.
    // Example:
    // findByUsername(String username)
    // becomes SQL automatically. like SELECT * FROM USER WHERE USERNAME = ?;

    // Find user by username
    Optional<User> findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find by employee id
    Optional<User> findByEmployeeId(Long employeeId);

    // Check username already exists
    boolean existsByUsername(String username);

    // Check email already exists
    boolean existsByEmail(String email);
}