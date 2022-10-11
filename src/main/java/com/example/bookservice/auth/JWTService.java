package com.example.bookservice.auth;

import com.example.bookservice.common.Common;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.audience}")
    private String audience;
    @Value("${jwt.expired}")
    private int expired;
    public String generateToken(UserDetails user) {
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam(Common.TYPE, Common.TOKEN_TYE)
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expired)).compact();
        return token;
    }
}
