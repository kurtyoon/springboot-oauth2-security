package dev.spring.security.oauth2.security.jwt;

import dev.spring.security.oauth2.constant.Constants;
import dev.spring.security.oauth2.domain.User;
import dev.spring.security.oauth2.dto.jwt.JwtTokenDto;
import dev.spring.security.oauth2.repository.UserRepository;
import dev.spring.security.oauth2.type.EUserType;
import dev.spring.security.oauth2.utility.JwtUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider implements InitializingBean {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte [] keyBytes = Decoders.BASE64.decode(jwtUtil.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String socialId, EUserType userType, boolean isAccess) {
        Claims claims = Jwts.claims();

        claims.put(Constants.SOCIAL_ID_CLAIM_NAME, socialId);
        if (isAccess) {
            claims.put(Constants.USER_TYPE_CLAIM_NAME, userType);
        }

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (
                        isAccess ? jwtUtil.getAccessTokenExpiration() : jwtUtil.getRefreshTokenExpiration()
                )))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtTokenDto createTokens(String socialId, EUserType userType) {
        return new JwtTokenDto(
                createToken(socialId, userType, true),
                createToken(socialId, userType, false)
        );
    }

    public String validateRefreshToken(HttpServletRequest request) {
        String refreshToken = resolveToken(request);
        Claims claims = validateToken(refreshToken);

        User user = userRepository.findBySocialIdAndRefreshToken(claims.get(Constants.SOCIAL_ID_CLAIM_NAME, String.class), refreshToken)
                .orElseThrow(() -> new JwtException("USER_NOT_FOUND"));
        return createToken(user.getSocialId(), user.getUserType(), true);
    }

    public String getSocialId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(Constants.SOCIAL_ID_CLAIM_NAME, String.class);
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.AUTHORIZATION);
        String newToken = null;

        if (token != null && token.startsWith(Constants.BEARER_PREFIX)) {
            newToken = token.substring(7);
        }
        return newToken;
    }
}
