package com.orange.task_management.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B59";

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(getKey()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }
}