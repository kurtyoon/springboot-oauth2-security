package dev.spring.security.oauth2.security.handler.jwt;

import dev.spring.security.oauth2.dto.exception.ExceptionDto;
import dev.spring.security.oauth2.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AbstractAuthenticationFailure {
    protected void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getStatus().value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", ExceptionDto.of(errorCode));

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
