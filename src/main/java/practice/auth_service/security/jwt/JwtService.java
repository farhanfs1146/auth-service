package practice.auth_service.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Secret key used to sign token
    // VERY IMPORTANT:
    // In real applications never hardcode secret keys.
    private static final String SECRET_KEY =
            "myVerySecretKeyForJwtTokenGeneration123456";

    // Token expiration time
    // 1000 = 1 second
    // 60 * 60 * 1000 = 1 hour
    private static final long ACCESS_TOKEN_EXPIRATION =
            1000 * 60 * 60;

    // Generate signing key object
    private Key getSigningKey() {

        // Keys.hmacShaKeyFor()
        // converts string secret into secure cryptographic key
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );
    }

    // Generate JWT token
    public String generateToken(String username) {

        Date now = new Date();

        Date expiryDate =
                new Date(
                        now.getTime() + ACCESS_TOKEN_EXPIRATION
                );

        return Jwts.builder()

                // subject/user identity
                .subject(username)

                // issued at
                .issuedAt(now)

                // expiration time
                .expiration(expiryDate)

                // sign token
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )


                // compact converts token into string
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {

        Claims claims =
                Jwts.parser()

                        .verifyWith((SecretKey) getSigningKey())

                        .build()

                        .parseSignedClaims(token)

                        .getPayload();

        return claims.getSubject();
    }

    // Validate token
    public boolean isTokenValid(String token, String username) {

        String extractedUsername =
                extractUsername(token);

        return extractedUsername.equals(username);
    }
}