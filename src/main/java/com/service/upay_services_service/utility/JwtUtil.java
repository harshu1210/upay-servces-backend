package com.service.upay_services_service.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.service.upay_services_service.enitites.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

    public static String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername()+" "+userDetails.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static boolean validateToken(String token, User userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()+" "+userDetails.getRole()) && !isTokenExpired(token);
    }

    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public static Date extractExpiration(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getExpiration();
    }

    private final static String SECRET = secretKeyUtility.generateSecretKey();
}
