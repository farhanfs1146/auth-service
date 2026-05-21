package practice.auth_service.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {

    // Permission id
    private Long id;

    // Permission name
    private String permissionName;

    // Permission description
    private String description;
}