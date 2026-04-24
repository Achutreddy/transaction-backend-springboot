package com.example.sample.sample1.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;


@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration:86400000}") //1day
    private Long jwtExpirationMs;

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512).compact();
    }
    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    public String getRole(String token){
        return (String) Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build().parseSignedClaims(token)
                .getPayload().get("role");
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build().parseSignedClaims(token);
            return true;
        }
        catch(JwtException | IllegalArgumentException ex){
            return false;
        }
    }
}
