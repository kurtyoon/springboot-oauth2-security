package dev.spring.security.oauth2.security.handler.jwt;

import dev.spring.security.oauth2.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint extends AbstractAuthenticationFailure implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = request.getAttribute("exception") == null ?
                ErrorCode.NOT_FOUND_ERROR : (ErrorCode) request.getAttribute("exception");
        setErrorResponse(response, errorCode);
    }
}
