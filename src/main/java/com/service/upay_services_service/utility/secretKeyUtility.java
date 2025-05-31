package com.service.upay_services_service.utility;

import java.util.Base64;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

public class secretKeyUtility {
    public static String generateSecretKey(){
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
