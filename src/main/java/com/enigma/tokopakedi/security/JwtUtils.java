package com.enigma.tokopakedi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.enigma.tokopakedi.entity.UserCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class JwtUtils {
    @Value("${app.tokopakedi.jwt-secret}")
    private String secretKey;

    @Value("${app.tokopakedi.jwt-expirationInSecond}")
    private Long expirationInSecond;
    @Value("${app.tokopakedi.jwt-app-name}")
    private String appName;

    public String generateToken(UserCredential user) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            List<String> roles = user.getRoles()
                    .stream()
                    .map(role -> role.getRole().name()).toList();
            return JWT.create()
                    .withIssuer(appName)
                    .withSubject(user.getId())
                    .withExpiresAt(Instant.now().plusSeconds(expirationInSecond))
                    .withClaim("roles", roles)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }


//    public String generateToken(UserCredential user){
//        try {
//            Algorithm algorithm = Algorithm.HMAC512(secretKey);
//            List<String> roles = user.getRoles().stream()
//                    .map(role -> role.getRoles().name()).toList();
//            return JWT.create()
//                    .withIssuer(appName)
//                    .withSubject(user.getId())
//                    .withExpiresAt(Instant.now().plusSeconds(expirationInSeconds))
//                    .withClaim("roles", roles)
//                    .sign(algorithm);
//        }catch (JWTCreationException e){
//            throw new RuntimeException(e);
//        }
//    }
}
