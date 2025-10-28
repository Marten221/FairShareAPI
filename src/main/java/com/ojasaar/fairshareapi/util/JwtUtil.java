package com.ojasaar.fairshareapi.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey SECRET_KEY;
    private final long EXPIRATION_TIME = 1000L * 60 * 60;//Long.parseLong(Dotenv.load().get("JWT_EXPIRATION_TIME"));

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public ResponseCookie generateCookie(String userId) {
        String token = generateToken(userId);

        return ResponseCookie.from("ACCESS_TOKEN", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(EXPIRATION_TIME / 1000)
                .build();
    }

    /**
     *
     * @param userId - id of user
     * @return JWT token
     */
    private String generateToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     *
     * @param token -
     * @return user's UUID
     */
    public String validate(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            throw new AuthorizationDeniedException("Invalid or Expired token");
        }
    }
}