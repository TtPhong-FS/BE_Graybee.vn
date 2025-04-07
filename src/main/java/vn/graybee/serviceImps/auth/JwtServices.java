package vn.graybee.serviceImps.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.graybee.models.users.UserPrincipalDto;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServices {

    private final SecretKey secretKey;

    private final int expiration;

    public JwtServices(
            @Value("${jwt.secretKey}") String random,
            @Value("${jwt.expiration}") int expiration

    ) {
        byte[] keyBytes = Decoders.BASE64.decode(random);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expiration = expiration;

    }

    private SecretKey getKey() {
        return secretKey;
    }

    public String generateToken(UserPrincipalDto user) {
        long now = System.currentTimeMillis();
        return Jwts
                .builder()
                .header().add("typ", "JWT").and()
                .claim("role", user.getROLE_NAME())
                .subject(user.getUsername())
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiration))
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
