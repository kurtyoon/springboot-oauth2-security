package dev.spring.security.oauth2.security.handler;

import com.google.gson.Gson;
import dev.spring.security.oauth2.dto.exception.ExceptionDto;
import dev.spring.security.oauth2.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setErrorResponse(response, ErrorCode.FORBIDDEN_ERROR);
    }

    public static void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("data", null);
        map.put("error", new ExceptionDto(errorCode));

        Gson gson = new Gson();
        String json = gson.toJson(map);
        response.getWriter().print(json);
    }
}
