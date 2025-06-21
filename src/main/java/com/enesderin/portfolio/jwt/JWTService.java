package com.enesderin.portfolio.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRET_KEY="tKjWiZGzEOmjdB+DAbGPSHi9vkWnC0l9U4H+f9FN7/0=";

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public <T> T exportToken(String token, Function<Claims, T> claimsFunction) {
        getClaims(token);
        return claimsFunction.apply(getClaims(token));
    }

    public String getUsernameToken(String token) {
        return exportToken(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        Date expiredDate = exportToken(token, Claims::getExpiration);
        return expiredDate.before(new Date());
    }

    public Key getKey() {
        byte[] keyBytes=  Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
