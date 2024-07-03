package com.example.demo.config.service;

import com.example.demo.config.UserPrinciple;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "123456789987654321123456789987654321123456789";
    private static final long EXPIRE_TIME = 86400000L;

    /**
     * Generates a JWT token for a successful login.
     *
     * @param authentication The Spring Security Authentication object containing user details.
     * @return A JWT token string.
     */
    public String generateTokenLogin(Authentication authentication) {
        // Extract user principal from the authentication object
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        /*
         * TODO: Implement a mechanism to use a stronger cryptographic algorithm
         *   Use HS256 algorithm for other applications to be able to use it
         */
        // Build and sign the JWT token
        /*TODO: builder-> set noi dung cho payload*/
        return Jwts.builder().setSubject((userPrincipal.getUsername())) // Set the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issue time
                .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME))  // Set the expiration time
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign the token with the secret key and HS256 algorithm
                .compact(); // Return the compact token string
    }

    /**
     * This method is used to generate a key for signing JWT tokens.
     *
     * @return A Key object that can be used for signing JWT tokens.
     */
    private Key getSignInKey() {
        // Decode the secret key from a base64 string
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        // Create a HMAC SHA key using the decoded key bytes
        // The HMAC SHA algorithm is used for signing the JWT tokens
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Validates the JWT token.
     *
     * @param authToken The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     * @throws MalformedJwtException    If the token is not well-formed.
     * @throws ExpiredJwtException      If the token has expired.
     * @throws UnsupportedJwtException  If the token is not supported.
     * @throws IllegalArgumentException If the token is empty.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token -> Message: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token -> Message: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token -> Message: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty -> Message: " + e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
    }
}
