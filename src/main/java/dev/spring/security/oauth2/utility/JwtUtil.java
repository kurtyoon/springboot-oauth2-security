package dev.spring.security.oauth2.utility;

import dev.spring.security.oauth2.constant.Constants;
import dev.spring.security.oauth2.dto.jwt.JwtTokenDto;
import dev.spring.security.oauth2.type.EUserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil implements InitializingBean {
    @Value("${security.jwt.secret}")
    private String secretKey;
    @Value("${security.jwt.access.expiration}")
    private Integer accessTokenExpiration;
    @Value("${security.jwt.refresh.expiration}")
    @Getter
    private Integer refreshTokenExpiration;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenDto generateTokens(Long id, EUserType userType) {
        return new JwtTokenDto(
                generateToken(id, userType, accessTokenExpiration * 1000),
                generateToken(id, userType, refreshTokenExpiration * 1000)
        );
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(Long id, EUserType userType, Integer expiration) {
        Claims claims = Jwts.claims();
        claims.put(Constants.USER_ID_CLAIM_NAME, id);
        if (userType != null) {
            claims.put(Constants.USER_TYPE_CLAIM_NAME, userType);
        }

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
