package practice.auth_service.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {

    // Role id
    private Long id;

    // Role name
    private String roleName;

    // Role description
    private String description;

    // Role permissions
    private Set<PermissionResponse> permissions;
}