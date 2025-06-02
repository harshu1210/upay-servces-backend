package com.service.upay_services_service.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.upay_services_service.enitites.User;
import com.service.upay_services_service.models.UserDTO;
import com.service.upay_services_service.repositories.UserRepo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtil {

    public String generateToken(UserDTO userDetails) throws JsonProcessingException {
        Map<String, Object> claims = new HashMap<>();

        String userJson = mapper.writeValueAsString(userDetails);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userJson)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutes
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    // --- Validate token from request ---
    public boolean validateToken(HttpServletRequest request) {
        try {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return false;
            }

            String token = authHeader.substring(7);
            String jsonPayload = extractSubject(token);
            JsonNode node = mapper.readTree(jsonPayload);

            if (!node.has("email")) {
                System.out.println("Missing 'email' field in token payload");
                return false;
            }

            String email = node.get("email").asText();
            Optional<User> userOpt = this.userRepo.findByEmail(email);

            return userOpt.isPresent() && !isTokenExpired(token);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String createdBY(HttpServletRequest request) {
        try {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return "";
            }

            String token = authHeader.substring(7);
            String jsonPayload = extractSubject(token);
            JsonNode node = mapper.readTree(jsonPayload);

            if (!node.has("email")) {
                System.out.println("Missing 'email' field in token payload");
                return "";
            }

            return node.get("email").asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // --- Extract subject (the embedded user JSON) ---
    public String extractSubject(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // --- Check expiration ---
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    // private final String SECRET = secretKeyUtility.generateSecretKey();
    private final String SECRET = secretKeyUtility.generateSecretKey();
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private UserRepo userRepo;

}
