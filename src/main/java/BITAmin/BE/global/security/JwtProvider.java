package BITAmin.BE.global.security;

import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import BITAmin.BE.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    private static final long accessTKExpirationTime = 7 * 24 * 60 * 60 * 1000L; // accessTK: 7일
    private static final long refreshTKExpirationTime = 30 * 24 * 60 * 60 * 1000L; //refreshTK: 30일
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // accessTK 생성
    public String createAccessToken(Member member) {
        Claims claims = getClaims(member);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTKExpirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    // refreshTK 생성
    public String createRefreshToken(Member member) {
        Claims claims = getClaims(member);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTKExpirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractAccessToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            return null;
        }
        return bearer.substring(7);
    }
    public String getUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    /**
     * tkn 유효성
     * @param token
     * @return
     */
    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
    private Claims getClaims(Member member) {
        Claims claims = Jwts.claims().setSubject(member.getEmail());
        claims.put("role","ROLE_"+member.getRole());
        return claims;
    }
}
