package vn.graybee.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.graybee.auth.config.JwtProperties;
import vn.graybee.auth.enums.Role;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey secretKey;

    private final JwtProperties jwtProperties;

    public JwtService(
            JwtProperties jwtProperties
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getAdminSecretKey());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.jwtProperties = jwtProperties;

    }

    private SecretKey getKey() {
        return secretKey;
    }

    public String generateToken(String uid, Role role) {
        long now = System.currentTimeMillis();
        return Jwts
                .builder()
                .header().add("typ", "JWT").and()
                .claim("role", role)
                .subject(uid)
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtProperties.getAdminExpiration()))
                .signWith(getKey())
                .compact();

    }

    public boolean isValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
