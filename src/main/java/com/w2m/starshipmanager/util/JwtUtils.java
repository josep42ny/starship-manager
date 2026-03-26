package com.w2m.starshipmanager.util;

import com.w2m.starshipmanager.data.model.JwtPrincipal;
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
    private static String jwtSecret;

    @Value("${sm.jwt.expiration}")
    private static long jwtExpiration;

    @Value("${sm.jwt.issuer}")
    private static String jwtIssuer;

    public static String generateAccessToken(final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();

        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return buildToken(claims, userDetails.getUsername(), jwtExpiration);
    }

    private static String buildToken(final Map<String, Object> claims, final String subject, final long expiration) {
        return Jwts.builder()
                .claims(claims)
                .issuer(jwtIssuer)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public static JwtPrincipal parseAndValidate(final String token) throws JwtException {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        validateIssuer(claims);

        return new JwtPrincipal(validateUsername(claims), validateRoles(claims));
    }

    private static List<String> validateRoles(final Claims claims) {
        final List<?> rawRoles = claims.get("roles", List.class);
        return (rawRoles == null)
                ? List.of()
                : rawRoles.stream().map(String::valueOf).toList();
    }

    private static String validateUsername(final Claims claims) {
        final String username = claims.getSubject();
        if (username == null || username.isBlank()) {
            throw new JwtException("Missing token subject");
        }
        return username;
    }

    private static void validateIssuer(final Claims claims) {
        final String tokenIssuer = claims.getIssuer();
        if (tokenIssuer == null || !tokenIssuer.equals(jwtIssuer)) {
            throw new JwtException("Invalid token issuer");
        }
    }

    private static SecretKey getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

