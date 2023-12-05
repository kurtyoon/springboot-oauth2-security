package dev.spring.security.oauth2.security.filter;

import dev.spring.security.oauth2.constant.Constants;
import dev.spring.security.oauth2.security.CustomUserDetails;
import dev.spring.security.oauth2.security.service.CustomUserDetailsService;
import dev.spring.security.oauth2.utility.HeaderUtil;
import dev.spring.security.oauth2.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new IllegalArgumentException("헤더에 토큰이 존재하지 않습니다."));
        Claims claims = jwtUtil.validateToken(token);

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserById(
                claims.get(Constants.USER_ID_CLAIM_NAME, Long.class)
        );

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
       return Constants.NO_AUTH_WHITE_LABEL.contains(request.getRequestURI()) || request.getRequestURI().startsWith("/guest");
    }
}
