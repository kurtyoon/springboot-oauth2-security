package dev.spring.security.oauth2.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class GlobalLoggerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("[Global Logger Filter] Request Received ({} {} {})",
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());

        request.setAttribute("Interceptor start tile", System.currentTimeMillis());

        filterChain.doFilter(request, response);

        Long preHandleTime = (Long) request.getAttribute("Interceptor start tile");
        Long postHandleTime = System.currentTimeMillis();

        log.info("[Global Logger Filter] HTTP Request Has Been Processed! It Takes {}ms. ({} {} {})",
                postHandleTime - preHandleTime,
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());
    }
}
