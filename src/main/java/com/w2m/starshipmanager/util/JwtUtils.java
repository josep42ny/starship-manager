package com.w2m.starshipmanager.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtils {

    @Value("${sm.jwt.secret}")
    private String jwtSecret;

    @Value("${sm.jwt.expiration}")
    private long jwtExpiration;

    @Value("${sm.jwt.issuer}")
    private String jwtIssuer;

    public String generateAccessToken(final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();

        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return this.buildToken(claims, userDetails.getUsername(), this.jwtExpiration);
    }

    private String buildToken(final Map<String, Object> claims, final String subject, final long expiration) {
        return Jwts.builder()
                .claims(claims)
                .issuer(this.jwtIssuer)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.getSigningKey())
                .compact();
    }

    public JwtPrincipal parseAndValidate(final String token) throws JwtException {
        final Claims claims = Jwts.parser()
                .verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        this.validateIssuer(claims);

        return new JwtPrincipal(this.validateUsername(claims), this.validateRoles(claims));
    }

    private List<String> validateRoles(final Claims claims) {
        final List<?> rawRoles = claims.get("roles", List.class);
        return (rawRoles == null)
                ? List.of()
                : rawRoles.stream().map(String::valueOf).toList();
    }

    private String validateUsername(final Claims claims) {
        final String username = claims.getSubject();
        if (username == null || username.isBlank()) {
            throw new JwtException("Missing token subject");
        }
        return username;
    }

    private void validateIssuer(final Claims claims) {
        final String tokenIssuer = claims.getIssuer();
        if (tokenIssuer == null || !tokenIssuer.equals(this.jwtIssuer)) {
            throw new JwtException("Invalid token issuer");
        }
    }

    private SecretKey getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

