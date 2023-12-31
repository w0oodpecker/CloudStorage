package com.example.cloudstorage.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private final String jwtAccessSecret;

    public JwtService(@Value("${jwt.secret.access}") String jwtAccessSecret) {
        this.jwtAccessSecret = jwtAccessSecret;
    }

    public String generateToken(UserDetails userDetails) { //Метод доступа к генерации токена
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) { //Генерация токена
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (999999 + 60 * 24)))
                .signWith(getSigndKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) { //Проверка токена
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) { //Проверка действия токена
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) { //Извлечение даты ококнчания срока токена
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { //Извлечение любого claim
        final Claims claims = extractAllClaims(token, getSigndKey());
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigndKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token) { //Извлечение логина из токена
        return extractClaim(token, Claims::getSubject);
    }

    private Key getSigndKey() {
        byte[] keyBytes = Decoders.BASE64.decode(String.valueOf(jwtAccessSecret));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
