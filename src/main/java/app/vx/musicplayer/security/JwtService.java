package app.vx.musicplayer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateToken (String login) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date (
                            System.currentTimeMillis() + 1000 * 60 * 60
                        )
                )
                .signWith(
                        getSignInKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public Key getSignInKey () {
        byte[] keyBytes = SECRET_KEY.getBytes();

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername (String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid (String token, String login) {
        String username = extractUsername(token);

        return username.equals(login) && !isTokenExpired(token);
    }

    private Claims extractAllClaims (String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired (String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
