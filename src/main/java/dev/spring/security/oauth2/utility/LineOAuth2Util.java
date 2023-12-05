package dev.spring.security.oauth2.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineOAuth2Util {
    /**
     * Line OAuth2
     */
    private String LINE_AUTHENTICATION_URL;
    private String LINE_TOKEN_URL;
    private String LINE_USER_INFO_URL;
    private String LINE_CLIENT_ID;
    private String LINE_CLIENT_SECRET;
    private String LINE_REDIRECT_URI;
}
