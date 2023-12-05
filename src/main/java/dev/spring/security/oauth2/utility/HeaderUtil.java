package dev.spring.security.oauth2.utility;

import dev.spring.security.oauth2.exception.CustomException;
import dev.spring.security.oauth2.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class HeaderUtil {
    public static Optional<String> refineHeader(HttpServletRequest request, String header, String prefix) {
        String bearerToken = request.getHeader(header);

        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(prefix)) {
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }
        return Optional.of(bearerToken.substring(prefix.length()));
    }
}
