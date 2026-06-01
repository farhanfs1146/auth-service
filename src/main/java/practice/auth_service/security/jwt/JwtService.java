package practice.auth_service.security.jwt;

import io.jsonwebtoken.*;

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
    private static final String SECRET_KEY = "myVerySecretKeyForJwtTokenGeneration123456";

    // Token expiration time
    // 1000 = 1 second
    // 60 * 60 * 1000 = 1 hour
    // private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60; // 1 hour for now.

    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 5; // 5 minutes for now.

    // Token expiration time now
    // for quick testing we can set it to 30 seconds
    // private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 30;

    // now are working on refresh token concept as well to refresh the access token after it expires.
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days validity.

    // Generate signing key object
    private Key getSigningKey() {

        // Keys.hmacShaKeyFor()
        // converts string secret into secure cryptographic key
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // updated method to add refresh JWT/Token after access JWT/Token expiration.
    // Why Separate Method?
    // because generateToken() is for access token which has shorter expiration time, while generateRefreshToken() is for refresh token which has longer expiration time,
    // so we can keep them separate for better clarity and maintainability.
    public String generateRefreshToken(String username) {

        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    // Generate JWT token
    public String generateToken(String username) {

        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);

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
    // currently we're just confirming & validating the JWT not handling exceptions
//    public boolean isTokenValid(String token, String username) {
//
//        String extractedUsername =
//                extractUsername(token);
//
//        return extractedUsername.equals(username);
//    }

    // VERY IMPORTANT UNDERSTANDING
    // Currently ( Old & Above Code ) your filter does:
    // Extract token
    //   ↓
    // Parse token
    //   ↓
    // Exception may happen
    //   ↓
    // Application crashes / ugly response


    // Better approach:
    // Extract token
    //   ↓
    // Try to parse token
    //   ↓
    // If parsing fails (invalid token, expired token, etc.)
    //   ↓
    // Catch exception and return false (invalid token)
    //   ↓
    // If parsing succeeds, validate username and return true (valid token)

    // or in simple words.

    // Extract token
    //   ↓
    // Safely validate token
    //   ↓
    // Catch JWT exceptions
    //   ↓
    // Return professional response

    // JWT EXCEPTIONS YOU MUST KNOW

    // | Exception                | Meaning            |
    // | ------------------------ | ------------------ |
    // | ExpiredJwtException      | token expired      |
    // | MalformedJwtException    | broken token       |
    // | SignatureException       | signature tampered |
    // | UnsupportedJwtException  | unsupported JWT    |
    // | IllegalArgumentException | empty token        |

    public boolean isTokenValid(String token, String username) {

        try {

            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);

        } catch (ExpiredJwtException ex) {
            System.out.println("JWT token expired");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("JWT unsupported");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims empty");
        }

        return false;
    }

    // to validate refresh JWT token
    public boolean isRefreshTokenValid(String token) {

        try {

            Jwts.parser()

                    .verifyWith((SecretKey) getSigningKey())

                    .build()

                    .parseSignedClaims(token);

            return true;

        } catch (JwtException ex) {

            return false;
        }
    }

    private boolean isTokenExpired(String token) {

        Date expirationDate = Jwts.parser()

                .verifyWith((SecretKey) getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload()

                .getExpiration();

        return expirationDate.before(new Date());
    }

    // VERY IMPORTANT LEARNING
    //WHAT IS getExpiration()?
    // It Reads:
    //"exp": 1779366363
    //from JWT payload.

    // WHAT DOES .before(new Date()) MEAN?
    // It Checks:
    // Is expiration time older than current time?
    // If yes: token expired else token valid.


}