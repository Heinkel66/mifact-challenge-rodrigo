package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/* Esta clase crea el token cuando el usuario se loguea 
y lo verifica cuando este hace alguna petición */
@Component
public class JwtUtil {

    // clave secreta para firmar el token
    private final String jwtSecret = "hBU9m1qRtWkfrGYZQLGbrPzan58cHDvO20XNuDJWRNyXEUZnxxWxJPLvMGGyoFuu2sYH8QyK4hck1RLucfaZhR";

    // tiempo de vida del token (ej. 10 horas)
    private final int jwtExpirationMs = 36000000;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
                | IllegalArgumentException e) {
            System.err.println("Error validando el token JWT: " + e.getMessage());
        }
        return false;
    }
}