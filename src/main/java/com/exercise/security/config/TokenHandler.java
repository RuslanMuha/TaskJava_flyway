package com.exercise.security.config;

import com.exercise.security.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class TokenHandler {
    @Value("${security.secretKey:erwqwgegre}")
    private  String secretKey;

    public Optional<String> extractUserId(@NonNull String token) {
        try {

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();

            return Optional.ofNullable(body.getId());

        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public String generateAccessToken(@NonNull String username, List<Role> roles, LocalDateTime expires) {
        Claims claims = Jwts.claims().setSubject(username);

        claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setId(username)
                .setExpiration(Date.from(expires.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

    }
}