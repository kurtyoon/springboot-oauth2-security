package dev.spring.security.oauth2.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.spring.security.oauth2.dto.auth.AuthLoginDto;
import dev.spring.security.oauth2.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String AUTH_PATH = "/api/auth/login";
    private static final String CONTENT_TYPE = "application/json";
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    protected CustomAuthenticationFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        super(AUTH_PATH);
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationException(ErrorCode.NOT_FOUND_ERROR.getMessage()){};
        }

        try {
            AuthLoginDto authLoginDto = objectMapper.readValue(request.getInputStream(), AuthLoginDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authLoginDto.socialId(), authLoginDto.password())
            );
            } catch (IOException e) {
            throw new AuthenticationException(ErrorCode.NOT_FOUND_ERROR.getMessage()){};
        }
    }
}
