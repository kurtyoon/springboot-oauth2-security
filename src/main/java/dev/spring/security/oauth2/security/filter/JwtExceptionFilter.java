package dev.spring.security.oauth2.security.filter;

import dev.spring.security.oauth2.constant.Constants;
import dev.spring.security.oauth2.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.error("JwtExceptionFilter");
        request.setAttribute("exception", ErrorCode.INTERNAL_SERVER_ERROR);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Constants.NO_AUTH_WHITE_LABEL.contains(request.getRequestURI()) || request.getRequestURI().startsWith("/guest");
    }
}
