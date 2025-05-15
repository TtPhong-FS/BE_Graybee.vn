package vn.graybee.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.graybee.config.PrefixJwtConfig;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServices {

    private final SecretKey secretKey;

    private final PrefixJwtConfig prefixJwtConfig;

    public JwtServices(
            PrefixJwtConfig jwtconfig
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtconfig.getSecretKey());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.prefixJwtConfig = jwtconfig;

    }

    private SecretKey getKey() {
        return secretKey;
    }

    public String generateToken(String username, String role) {
        long now = System.currentTimeMillis();
        return Jwts
                .builder()
                .header().add("typ", "JWT").and()
                .claim("role", role)
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + prefixJwtConfig.getExpiration()))
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
