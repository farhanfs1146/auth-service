package practice.auth_service.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResponse {

    // JWT access token
    private String accessToken;

    // JWT refresh token
    private String refreshToken;

    // Now login returns:
    // {
    //   "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY5ODQ4ODAwMCwiZXhwIjoxNjk4NDkyNjAwfQ.abc123",
    //   "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY5ODQ4ODAwMCwiZXhwIjoxNjk4NTMyMDAwfQ.def456"
    // }
}
