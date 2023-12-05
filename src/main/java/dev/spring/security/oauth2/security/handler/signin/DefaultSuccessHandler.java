package dev.spring.security.oauth2.security.handler.signin;

import dev.spring.security.oauth2.dto.jwt.JwtTokenDto;
import dev.spring.security.oauth2.repository.UserRepository;
import dev.spring.security.oauth2.security.CustomUserDetails;
import dev.spring.security.oauth2.utility.CookieUtil;
import dev.spring.security.oauth2.utility.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DefaultSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        JwtTokenDto tokenDto = jwtUtil.generateTokens(userDetails.getId(), userDetails.getUserType());
        userRepository.updateRefreshTokenAndLoginStatus(userDetails.getId(), tokenDto.refreshToken(), true);

        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            setSuccessAppResponse(response, tokenDto);
        } else {
            setSuccessWebResponse(response, tokenDto);
        }
    }

    /**
     * App에서 로그인 성공시 응답
     * @param response
     * @param tokenDto
     * @throws IOException
     */
    private void setSuccessAppResponse(HttpServletResponse response, JwtTokenDto tokenDto) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", Map.of(
                "accessToken", tokenDto.accessToken(),
                "refreshToken", tokenDto.refreshToken()
        ));
        result.put("error", null);

        response.getWriter().write(JSONValue.toJSONString(result));
    }

    /**
     * Web에서 로그인 성공시 응답
     * @param response
     * @param tokenDto
     * @throws IOException
     */
    private void setSuccessWebResponse(HttpServletResponse response, JwtTokenDto tokenDto) throws IOException {
        CookieUtil.addCookie(response, "access_token", tokenDto.accessToken());
        CookieUtil.addSecureCookie(response, "refresh_token", tokenDto.refreshToken(), jwtUtil.getRefreshTokenExpiration());
        response.sendRedirect("/");
    }
}
