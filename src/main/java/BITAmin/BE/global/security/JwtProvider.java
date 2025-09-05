package BITAmin.BE.global.security;

import BITAmin.BE.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
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

    private static final long accessTKExpirationTime = 7 * 24 * 60 * 60 * 1000L; // accessTK: 1일
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
    private Claims getClaims(Member member) {
        Claims claims = Jwts.claims().setSubject(member.getEmail());
        claims.put("role", member.getRole());
        return claims;
    }
}
